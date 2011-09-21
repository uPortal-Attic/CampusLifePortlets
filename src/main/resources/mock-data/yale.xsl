<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="no"/>

    <xsl:template match="/">
        <menu
            xmlns="https://source.jasig.org/schemas/portlet/dining/menu"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="https://source.jasig.org/schemas/portlet/dining/menu ../xsd/menu.xsd"
            day="Today">
            <dining-hall name="{//a[@href='#tabs-1']}">
                <xsl:for-each select="./descendant::div[@class='meal']">
                    <xsl:apply-templates select="."/>
                </xsl:for-each>
            </dining-hall>
        </menu>
    </xsl:template>

    <xsl:template match="div[@class='meal']">
        <meal name="{h1}">
            <xsl:for-each select="./descendant::div[@class='course']">
                <xsl:apply-templates select="."/>
            </xsl:for-each>
        </meal>
    </xsl:template>
  
    <xsl:template match="div[@class='course']">
        <food-category name="{substring-before(span[position()=1],' ~')}">
            <xsl:for-each select="./descendant::div[@class='serving']">
                <xsl:apply-templates select="."/>
            </xsl:for-each>
        </food-category>
    </xsl:template>
  
    <xsl:template match="div[@class='serving']">
        <dish name="{div[@class='recipe']/*}">
            <xsl:for-each select="./descendant::div[@class='icons']/a">
                <code><xsl:value-of select="substring-after(./@href,'=')"/></code>
            </xsl:for-each>
            <xsl:for-each select="./following-sibling::div[position()=1]/div[@class='nutrTwoCol']/h2">
                <nutrition-item name="{substring-before(.,':')}" value="{./following-sibling::p[position()=1]}"/>
            </xsl:for-each>
            <ingredients><xsl:value-of select="./following-sibling::div[position()=1]/div[@class='nutrOneCol']/p"/></ingredients>
        </dish>
    </xsl:template>
  
</xsl:stylesheet>