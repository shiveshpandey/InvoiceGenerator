package com.invoice.generator;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

public interface InvoiceWebConstants {

    public static final Font font1 = FontFactory.getFont(BaseFont.TIMES_ROMAN, 12, Font.BOLD);
    public static final Font font2 = FontFactory.getFont(BaseFont.TIMES_ROMAN, 12, Font.NORMAL);
    public static final Font font3 = FontFactory.getFont(BaseFont.TIMES_ROMAN, 12, Font.NORMAL);
    public static final Font font5 = FontFactory.getFont(BaseFont.TIMES_ROMAN, 9, Font.NORMAL);
    public static final Font font12 = FontFactory.getFont(BaseFont.TIMES_ROMAN, 13,
            Font.UNDERLINE + Font.BOLDITALIC);

    public String filePath = "/Download.pdf";
    public static final int BUFFER_SIZE = 4096;
    public String footerMsg1 = "This is a computer generated invoice. No signature required.";
    public String footerMsg2 = "Thank you for shopping with us.";
}
