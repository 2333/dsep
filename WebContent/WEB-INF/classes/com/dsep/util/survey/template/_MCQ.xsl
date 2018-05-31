<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/question">
		<div class="publishQ">
			<xsl:variable name="qId" select="qId" />
			<span class="publishQStem">
				<xsl:value-of select="qStem" />
				(多选)
			</span>
			<table>
				<xsl:for-each select="qItems/singleItem">
					<tr>
						<td>
							<input type="checkbox" class="value">
								<xsl:attribute name="name">
     							<xsl:value-of select="parentQRefId" />
     							</xsl:attribute>
     							<xsl:attribute name="value">
     								<xsl:value-of select="value" />
     							</xsl:attribute>
								<xsl:attribute name="itemId">
  								<xsl:value-of select="itemId" />
								</xsl:attribute>
							</input>
							<span><xsl:value-of select="str1" /></span>
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</div>
	</xsl:template>
</xsl:stylesheet> 