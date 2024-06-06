package com.pranavv51.microservice.xlreadmicroservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@FeignClient("awsbridge-application")
public interface AWSProxy {

    @PutMapping(value="aws-client-handler/add-scratch-file/{username}")
    public String addscratchFile(@PathVariable String username,@RequestBody byte[] fileArray);

}
