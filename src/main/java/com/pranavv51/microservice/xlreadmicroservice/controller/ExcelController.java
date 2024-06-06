package com.pranavv51.microservice.xlreadmicroservice.controller;


import com.pranavv51.microservice.xlreadmicroservice.proxy.AWSProxy;
import com.pranavv51.microservice.xlreadmicroservice.proxy.PdfProxy;
import com.pranavv51.microservice.xlreadmicroservice.service.ExcelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value="get-list-from-xl/")
@CrossOrigin
public class ExcelController {

    //http://localhost:8906/get-list-from-xl/xl-file
    private final ExcelService serviceInst;

    private final PdfProxy pdfproxyInst;

    private final AWSProxy awsProxyInst;

    public ExcelController(ExcelService serviceInst, PdfProxy pdfproxyInst, AWSProxy awsProxyInst) {
        this.serviceInst = serviceInst;
        this.pdfproxyInst = pdfproxyInst;
        this.awsProxyInst = awsProxyInst;
    }

    public static void byteArrayToPdf(byte[] byteArray, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }



    @CrossOrigin
    @PostMapping(value="xl-file/username/{userName}")
    public List<String> returnList(@PathVariable String userName, @RequestParam("xlfile") MultipartFile fileInst) throws Exception {
        List<List<String>> scratch;
        try {
            scratch = serviceInst.readAnXlFile(fileInst);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        byte[] tempByteArray = pdfproxyInst.generatePdf(scratch).getBody();

        List<String> primeTuples = new LinkedList<>();
        int c=0;
        for(List<String> name: scratch){
            primeTuples = name;
            c++;
            if(c>0)
                break;
        }

        awsProxyInst.addscratchFile(userName,tempByteArray);
        return primeTuples;

    }


}
