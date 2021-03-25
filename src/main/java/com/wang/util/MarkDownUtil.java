package com.wang.util;

import com.overzealous.remark.Remark;
import com.overzealous.remark.util.MarkdownTable;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;

public class MarkDownUtil {
    /**
     * 转换md格式为html
     *
     * @param markdownString
     * @return
     */
    public static String mdToHtml(String markdownString) {
        if (StringUtils.isEmpty(markdownString)) {
            return "";
        }
        java.util.List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        return content;
    }

    public  static String htmlToMd(String htmlString){
        Remark remark=new Remark();
        return remark.convertFragment(htmlString);
    }
}
