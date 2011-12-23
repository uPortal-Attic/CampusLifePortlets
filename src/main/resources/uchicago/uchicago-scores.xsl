<!--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="no"/>

    <xsl:template match="/">
        <sport name="Football"
            xmlns="https://source.jasig.org/schemas/portlet/athletics/athletics-feed"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="https://source.jasig.org/schemas/portlet/athletics/athletics-feed ../xsd/athletics-feed.xsd">
            <xsl:apply-templates select="//span[contains(text(),'2011') and contains(text(),'Schedule &amp;amp; Results')]/following-sibling::table[position()=1]"/>
        </sport>
    </xsl:template>
  
    <xsl:template match="table">
        <xsl:apply-templates select="tr[position() &gt; 1]"/>
    </xsl:template>
    
    <xsl:template match="tr">
        <competition name="{td[position()=3]}" date="{td[position()=2]}" location="{td[position()=4]}">
            <xsl:choose>
                <xsl:when test="count(td[position()=5]/a) &gt; 0">
                    <xsl:attribute name="result"><xsl:value-of select="td[position()=5]"/></xsl:attribute>
                    <xsl:attribute name="url"><xsl:value-of select="td[position()=5]/a/@href"/></xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="time"><xsl:value-of select="td[position()=5]"/></xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
        </competition>
    </xsl:template>
  
</xsl:stylesheet>