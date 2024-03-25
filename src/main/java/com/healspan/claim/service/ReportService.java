package com.healspan.claim.service;

import com.healspan.claim.model.s3.ReportResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {
    ReportResponse patientReport(String fileName, Long claimInfoId) throws JRException, FileNotFoundException;
}
