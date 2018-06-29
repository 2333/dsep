package com.dsep.util.export.briefsheet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.TrueTypeUtil;

import com.dsep.util.briefsheet.PathProvider;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
/**
 * ITextRenderer对象工厂,提供性能,加载中文字体集(大小20M),故增加对象池
 * @ClassName: ITextRendererObjectFactory
 * @Description:
 * @author lihengjun
 * 修改时间： 2013年11月13日 下午3:00:20
 * 修改内容：新建
 */
public class ITextRendererObjectFactory extends
		BasePoolableObjectFactory {
	private static GenericObjectPool itextRendererObjectPool = null;

	@Override
	public Object makeObject() throws Exception {
		ITextRenderer renderer = createTextRenderer();
		return renderer;
	}
	/**
	 * 获取对象池,使用对象工厂 后提供性能,能够支持 500线程 迭代10
	 * @Title: getObjectPool
	 * @Description: 获取对象池
	 * @return GenericObjectPool
	 * @author lihengjun
	 * 修改时间： 2013年11月13日 下午2:15:15
	 * 修改内容：新建
	 */
	public static GenericObjectPool getObjectPool(){
		synchronized (ITextRendererObjectFactory.class) {
			if(itextRendererObjectPool==null){
				itextRendererObjectPool = new GenericObjectPool(new ITextRendererObjectFactory());
				GenericObjectPool.Config config = new GenericObjectPool.Config();
				config.lifo = false;
				config.maxActive = 15;
				config.maxIdle = 5;
				config.minIdle = 1;
				config.maxWait = 5 * 1000;
				itextRendererObjectPool.setConfig(config);
			}
		}
		
		return itextRendererObjectPool;
	}

	/**
	 * 初始化
	 * 
	 * @Title: initTextRenderer
	 * @Description:
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 * @author lihengjun 修改时间： 2013年11月13日 上午10:39:39 修改内容：新建
	 */
	public static synchronized ITextRenderer createTextRenderer()
			throws DocumentException, IOException {
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// 解决图片的相对路径问题    
		/*renderer.getSharedContext().getUserAgentCallback().setBaseURL("file:/E:/project7/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/"); */
		addFonts(fontResolver);
		return renderer;
	}

	/**
	 * 添加字体
	 * 
	 * @Title: addFonts
	 * @Description:
	 * @param fontResolver
	 * @throws DocumentException
	 * @throws IOException
	 * @author lihengjun 修改时间： 2013年11月5日 下午1:37:57 修改内容：新建
	 */
	public static ITextFontResolver addFonts(ITextFontResolver fontResolver)
			throws DocumentException, IOException {
		// Font fontChinese = null;
		// BaseFont bfChinese = BaseFont.createFont("STSong-Light",
		// "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// fontChinese = new Font(bfChinese, 12, Font.NORMAL);

		File fontsDir = new File(PathProvider.getTemplateRootPath(), "fonts");
		if (fontsDir != null && fontsDir.isDirectory()) {
			File[] files = fontsDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f == null || f.isDirectory()) {
					break;
				}
				String fontString = f.getAbsolutePath();
				BaseFont font;
				fontResolver.addFont(fontString, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
				if(fontString.substring(fontString.lastIndexOf(".")+1).equals("ttc")){
					font = BaseFont.createFont(f.getAbsolutePath()+",0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				}else{
					font = BaseFont.createFont(f.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				}
				String[] fontFamilyName = TrueTypeUtil.getFamilyNames(font);
				System.out.println(i+": "+Arrays.toString(fontFamilyName));
			}
		}
		return fontResolver;
	}
}