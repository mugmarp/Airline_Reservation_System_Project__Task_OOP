/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;
import java.util.List;

/**
 * Response for generating a report. Contains the data rows as List<String[]>.
 * Each String[] represents one row, with column values as strings.
 */
public class GenerateReportResponse extends Response {
    private static final long serialVersionUID = 1L;
    
    private List<String[]> headers;  // Column headers
    private List<String[]> rows;     // Data rows
    private String reportTitle;      // Report title
    private String fileName;         // Suggested file name
    
    public GenerateReportResponse(boolean success, String reportTitle, List<String[]> headers, List<String[]> rows, String fileName) {
        super(success);
        this.reportTitle = reportTitle;
        this.headers = headers;
        this.rows = rows;
        this.fileName = fileName;
    }
    
    public GenerateReportResponse(boolean success, String message) {
        super(success, message);
    }
    
    public List<String[]> getHeaders() { return headers; }
    public void setHeaders(List<String[]> headers) { this.headers = headers; }
    public List<String[]> getRows() { return rows; }
    public void setRows(List<String[]> rows) { this.rows = rows; }
    public String getReportTitle() { return reportTitle; }
    public void setReportTitle(String reportTitle) { this.reportTitle = reportTitle; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}