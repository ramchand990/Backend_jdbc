package com.healspan.claim.service;

import com.google.gson.Gson;
import com.healspan.claim.model.s3.ReportDto;
import com.healspan.claim.model.s3.ReportResponse;
import com.healspan.claim.util.ReportUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Value(("${file.path}"))
    private String path;
    @Autowired
    private ReportUtil reportUtil;
    @Autowired
    private Gson gson;

    public ReportResponse patientReport(String reportName, Long claimInfoId) {
        logger.info("ReportServiceImpl::patientReport Request received for patient reports for claimId: {} --Start", claimInfoId);
        byte[] bytes = null;
        ReportResponse response = null;
        List<ReportDto> reportData = null;
        JasperReport jasperReport = null;
        try {
            File reportDesign = new File(path+"healspan.jrxml");
            logger.info("Report Design Path : {}", reportDesign.getAbsolutePath());
            File logo = new File(path+"healspan.png");
            logger.info("HealSpan logo Path : {}", logo.getAbsolutePath());
            InputStream inputStream = Files.newInputStream(reportDesign.toPath());
            jasperReport = JasperCompileManager.compileReport(inputStream);
            reportData = reportUtil.generateReportData(claimInfoId);
            if (reportData !=null) {
                response = new ReportResponse();
                JRDataSource jrDataSource = new JRBeanCollectionDataSource(reportData);
                String data = new Gson().toJson(jrDataSource);
                logger.debug("Patient Details : {}",data);
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("data_source", jrDataSource);
                parameters.put("image_path", logo.getAbsoluteFile().toString());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
                bytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setReportData(bytes);
                response.setFileName(reportData.get(0).getName() + ".pdf");
            }
        } catch (Exception ex) {
            logger.error("ReportServiceImpl::patientReport::Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
        logger.info("ReportServiceImpl::patientReport responseData: {} --End", response);
        return response;
    }
}
