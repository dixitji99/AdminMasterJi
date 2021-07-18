package com.masterji.admin;

public class PdfData {
   String pdfUrl,pdftitle;

    public PdfData() {
    }

    public PdfData(String pdfUrl, String pdftitle) {
        this.pdfUrl = pdfUrl;
        this.pdftitle = pdftitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdftitle() {
        return pdftitle;
    }

    public void setPdftitle(String pdftitle) {
        this.pdftitle = pdftitle;
    }
}