<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<div class="publishQ">
			<xsl:variable name="qId" select="question/qId" />
			<span class="publishQStem">
				<xsl:value-of select="question/qStem" />
			</span>
			<table class="matrixTb">
				<xsl:for-each select="question/subQuestion">
					<tr>
						<td>
							<xsl:value-of select="qStem" />
							<input type="hidden" name="qId">
								<xsl:attribute name="value">
	        					<xsl:value-of select="qId" />
	        					</xsl:attribute>
							</input>
						</td>
						<xsl:for-each select="qItems/singleItem">
							<td>
								<xsl:if test="selectBoxNum='1'">
									<input type="radio" class="value">
										<xsl:attribute name="value">
        								<xsl:value-of select="value" />
        								</xsl:attribute>
										
										<xsl:attribute name="name">
        								<xsl:value-of select="parentQRefId" />
        								</xsl:attribute>
										
										<xsl:attribute name="itemId">
        								<xsl:value-of select="itemId" />
        								</xsl:attribute>
									</input>
								</xsl:if>
								<xsl:value-of select="str1" />
							</td>
						</xsl:for-each>
					</tr>
				</xsl:for-each>
			</table>
		</div>
	</xsl:template>

</xsl:stylesheet>