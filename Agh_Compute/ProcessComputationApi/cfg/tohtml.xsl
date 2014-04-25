<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head><title>Wynik</title></head>
			<body>
				<table>
					<TH>Wartość</TH>
					<th>Rank</th>
					<th>Zmienne</th>
					<xsl:for-each select="//genetic.Individual">
						<tr>
							<td style="border-bottom-style:solid; border-color:black; border-width:1px"><xsl:value-of select="result"/></td>
							<td style="border-bottom-style:solid; border-color:black;border-width:1px"><xsl:value-of select="rank"/></td>
							<td style="font-size:xx-small;border-bottom-style:solid; border-color:black;border-width:1px">
									<xsl:for-each select="problemVariables/double">
										<xsl:value-of select="."/><br/>
									</xsl:for-each>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>