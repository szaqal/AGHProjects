<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="xml" indent="yes" />

	<xsl:template match="/">

		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="stronaA4"
					page-height="29.7cm" page-width="21.0cm" margin="2cm">
					<fo:region-body margin="1cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="stronaA4">
				<fo:flow flow-name="xsl-region-body">
					<fo:table>
						<fo:table-column column-width="20mm" />
						<fo:table-column column-width="45mm" />
						<fo:table-column column-width="45mm" />

						<fo:table-header>
							<fo:table-row>
								<fo:table-cell>
									<fo:block font-weight="bold">Rank</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block font-weight="bold">Rezultat</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block font-weight="bold">Zmienne</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-header>

						<fo:table-body>
							<xsl:for-each select="//genetic.Individual">
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="rank" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="result" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										<fo:list-block>
											<xsl:for-each select="/problemVariables/double">
												<fo:list-item>
													<fo:list-item-label>
														<fo:block></fo:block>
													</fo:list-item-label>
													<fo:list-item-body>
														<fo:block><xsl:value-of select="." /></fo:block>
													</fo:list-item-body>
												</fo:list-item>
											</xsl:for-each>
											</fo:list-block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:for-each>
						</fo:table-body>
					</fo:table>

				</fo:flow>
			</fo:page-sequence>
		</fo:root>

	</xsl:template>

</xsl:stylesheet>


