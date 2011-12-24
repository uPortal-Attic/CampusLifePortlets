/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.campuslife.athletics.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.AthleticsFeed;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.Sport;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.core.io.Resource;

/**
 * ScreenScrapingAthleticsDaoImpl provides a reusable athletics DAO 
 * implementation targeted for collecting information from HTML content.  
 * Whenever possible, results should instead be retrieved from some more 
 * well-formatted web service or other high quality data source. 
 * 
 * This implementation uses OWASP AntiSamy to clean and validate external
 * HTML pages, then uses an XSLT to transform the HTML page into the 
 * portlet's default athletics feed XML structure, at which point the data
 * can be deseriablized.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @Revision $Revision$
 */
public class ScreenScrapingAthleticsDaoImpl implements IAthleticsDao {

    protected Log log = LogFactory.getLog(getClass());

    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    
    private Resource xslt;
    
    /**
     * Set the XSLT to be used to transform the cleaned and validated HTML to
     * the portlet's default XML strucutre. 
     * 
     * @param xslt
     */
    public void setXslt(Resource xslt) {
        this.xslt = xslt;
    }
    
    private Policy policy;
    
    /**
     * Set the AntiSamy policy file to be used to clean and validate external
     * HTML.
     * 
     * @param config
     * @throws PolicyException
     * @throws IOException
     */
    public void setPolicy(Resource config) throws PolicyException, IOException {
        this.policy = Policy.getInstance(config.getFile());
    }
    
    private Map<String,String> sportUrls;

    /**
     * Set the mapping of URLs for each sport.  This implementation assumes that
     * each sport is represented by its own HTML page.
     * 
     * @param urlMap
     */
    public void setSportUrls(Map<String,String> urlMap) {
        this.sportUrls = urlMap;
    }

    /**
     * Get the mapping of URLs by sport.
     * 
     * @return
     */
    public Map<String,String> getSportUrls() {
        return this.sportUrls;
    }

    @Override
    public AthleticsFeed getFeed() {
        
        final AthleticsFeed feed = new AthleticsFeed();

        // Retrieve and parse each URL in the sport URL mapping and assemble
        // the results into a single athletics feed.
        for (String sportKey : sportUrls.keySet()) {
            final Sport sport = getSport(sportKey);
            if (sport != null) {
                feed.getSport().add(sport);
            }
        }
        
        return feed;
    }

    @Override
    public Sport getSport(String sportKey) {
        
        // get the URL associated with this sport key
        final String url = sportUrls.get(sportKey);        
        if (url != null) {
            
            try {
                
                // get the HTML for the requested sport, ensure it represents
                // valid XML, and deserialize it into a Sport object
                final String html = getHtmlContent(url);
                final String cleanHtml = getCleanedHtmlContent(html);
                final String xml = getXml(cleanHtml);
                final Sport sport = getSportForXml(xml);
                sport.setName(sportKey);
                
                // perform any required post-processing
                postProcessSport(sport);
                
                return sport;
                
            } catch (ClientProtocolException e) {
                log.error(e);
            } catch (IOException e) {
                log.error(e);
            } catch (ScanException e) {
                log.error(e);
            } catch (PolicyException e) {
                log.error(e);
            } catch (TransformerException e) {
                log.error(e);
            } catch (JAXBException e) {
                log.error(e);
            }
        }
        
        return null;
    }
    
    /**
     * Get the raw HTML content for a specified URL.
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String getHtmlContent(String url) throws ClientProtocolException, IOException {
        final DefaultHttpClient httpclient = new DefaultHttpClient();
        final HttpGet httpget = new HttpGet(url);
        final HttpResponse response = httpclient.execute(httpget);
        final InputStream httpStream = response.getEntity().getContent();
        final String content = IOUtils.toString(httpStream);
        return content;
    }

    /**
     * Clean and validate raw HTML, returning valid XML.
     * 
     * @param html
     * @return
     * @throws ScanException
     * @throws PolicyException
     */
    protected String getCleanedHtmlContent(String html) throws ScanException, PolicyException {
        final AntiSamy as = new AntiSamy();
        final CleanResults cr = as.scan(html, policy);
        final String cleanedHtml = cr.getCleanHTML();  
        final String unescaped = cleanedHtml.replace("&", "&amp;");
        return unescaped;
    }

    /**
     * Transform clean and valid HTML into the portlet's default athletics
     * format XML feed.
     * 
     * @param cleanHtml
     * @return
     * @throws TransformerException
     * @throws IOException
     */
    protected String getXml(String cleanHtml) throws TransformerException, IOException {
        final StreamSource xsltSource = new StreamSource(xslt.getInputStream());
        final Transformer identityTransformer = transformerFactory.newTransformer(xsltSource);
        identityTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        final StringWriter outputWriter = new StringWriter();
        final StreamResult outputTarget = new StreamResult(outputWriter);
        final StreamSource xmlSource = new StreamSource(new StringReader(cleanHtml));
        identityTransformer.transform(xmlSource, outputTarget);
        final String content = outputWriter.toString();
        return content;
    }
    
    /**
     * Deserialize athletics feed XML into a Sport object.
     * 
     * @param xml
     * @return
     * @throws JAXBException
     */
    protected Sport getSportForXml(String xml) throws JAXBException {
        final String packageName = Sport.class.getPackage().getName();
        final JAXBContext jc = JAXBContext.newInstance( packageName );
        final Unmarshaller u = jc.createUnmarshaller();
        final Sport sport = (Sport) u.unmarshal(IOUtils.toInputStream(xml));
        return (Sport) sport;
    }
    
    /**
     * Optional post-processing method allows subclasses to add custom cleanup
     * logic after deserialization.  The default implementation does nothing.
     * 
     * @param sport
     */
    protected void postProcessSport(Sport sport) {
        // default implementation does nothing
    }

}
