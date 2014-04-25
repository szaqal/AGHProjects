<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:strip-space elements="*" />
    <xsl:output method="text" omit-xml-declaration="yes"></xsl:output>
    <xsl:template match="/map/entry/string/list/genetic.Individual">
        <xsl:apply-templates select="problemvariables" />
    </xsl:template>
    
    

    <xsl:template match="problemVariables">
    <xsl:value-of select="../rank"></xsl:value-of><xsl:text>,</xsl:text>
        <xsl:for-each select="*">
            <xsl:value-of select="." />
            <xsl:if test="position() != last()">
                <xsl:value-of select="','" />
            </xsl:if>
        </xsl:for-each>
        <xsl:text>,</xsl:text>
       <xsl:value-of select="../result"></xsl:value-of>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>


    <xsl:template match="/map/entry/string">
    </xsl:template>

    <xsl:template match="lBound">
    </xsl:template>

    <xsl:template match="uBound">
    </xsl:template>

    <xsl:template match="result">
    </xsl:template>

    <xsl:template match="rank">
    </xsl:template>
    
</xsl:stylesheet>