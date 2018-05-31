<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/question">
		<div class="publishQHint">
			<span class="">
				<xsl:value-of select="qStem"></xsl:value-of>
			</span>
			<hr/>
		</div>
	</xsl:template>

</xsl:stylesheet>