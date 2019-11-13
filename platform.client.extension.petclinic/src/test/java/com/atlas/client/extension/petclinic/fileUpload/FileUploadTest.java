package com.atlas.client.extension.petclinic.fileUpload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.domain.cmd.exec.CommandExecution.MultiOutput;
import com.antheminc.oss.nimbus.domain.cmd.exec.CommandExecution.Output;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig;
import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.antheminc.oss.nimbus.domain.model.state.internal.DefaultListParamState;
import com.antheminc.oss.nimbus.support.Holder;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.antheminc.oss.nimbus.test.domain.support.utils.ParamUtils;
import com.atlas.client.extension.petclinic.core.FileAttachment;
import com.atlas.client.extension.petclinic.scenariotests.AbstractPetclinicSpringTest;
import com.atlas.client.extension.petclinic.view.home.FileLineItem;
import com.atlas.client.extension.petclinic.view.home.VPNotes;

public class FileUploadTest extends AbstractPetclinicSpringTest  {
	
public static String PLATFORM_ROOT = 	"/"+CLIENT_ID+"/"+CLIENT_ORG+"/p";
public static String basePageUrl = PLATFORM_ROOT+"/petclinicdashboard/vpNotes";


	@Test
	public void testFileUploadDownloadDelete() throws IOException {
		// Open the Notes Page
		// Click on Add source 
		// Add some extra form details
		// Click form Submit 
		// Test the Grid line Item Size - if its 1 
		// Test the attributes in respective columns
		// Click on Download Link - assert the return content 
	 
		
		String fileName = "gotham.txt";
		String fileContent = "Beware!! This is Batman's file";
		String fileType = "text/plain";
		String description = "Batman's secret file";
		String comments = "Lets check this out";
	
		MultipartFile file = new MockMultipartFile(fileName,
			fileName,
			fileType,
            fileContent.getBytes(StandardCharsets.UTF_8));
		
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(basePageUrl)
				.addAction(Action._get)
				.getMock();
		
		Object notesPage = this.controller.handlePost(request, null);
		Param<VPNotes> param = ParamUtils.extractResponseByParamPath(notesPage, "/vpNotes");
		
		Param<?> fileControlParam = param.findParamByPath("/vtNotes/vsFiles/vfFile/fileControl");
		String fileUploadUrl = (String) fileControlParam.getConfig().getUiStyles().getAttributes().get("url");
		String taregtParam =  fileControlParam.getPath()+ (String) fileControlParam.getConfig().getUiStyles().getAttributes().get("targetParam");
		
		String uploadUri = PLATFORM_ROOT+fileUploadUrl;
		MockHttpServletRequest httpUploadReq = MockHttpRequestBuilder.withUri(uploadUri).addParam("targetPath", taregtParam).addParam("description",description ).addParam("comments", comments).getMock();
		
		Holder uploadResponseHolder =  (Holder) controller.handleFileUpload(httpUploadReq, file);
		
		MultiOutput multiOutputResponse = (MultiOutput) uploadResponseHolder.getState();
	
	
		Output<?> out = multiOutputResponse.getOutputs().stream().filter(output -> output.getAction().equals(Action._new)).findFirst().get();
		DefaultListParamState<?> paramState = (DefaultListParamState<?>) out.getValue();
		
		
		List<FileAttachment> uploadresponse = (List<FileAttachment>) paramState.getState();
		
		//assert the uploadResponse has One Object with the same file Name
		assertEquals(1,uploadresponse.size());
		assertEquals(uploadresponse.get(0).getFileName(),fileName);
		assertEquals(uploadresponse.get(0).getComments(),comments);
		assertEquals(uploadresponse.get(0).getDescription(),description);
		assertEquals(uploadresponse.get(0).getFileType(),fileType);
		assertNotNull(uploadresponse.get(0).getId());
		assertNotNull(uploadresponse.get(0).getExternalId());
		
		// Now invoke the Submit Button and check if the grid is populated
			
		MockHttpServletRequest fileUploadSubmitRequest = MockHttpRequestBuilder.withUri(basePageUrl)
			
				.addNested("/vtNotes/vsFiles/vfFile/submitBG/upload")
				.addAction(Action._get)
				.getMock();
		Object submit = this.controller.handlePost(fileUploadSubmitRequest, null);
		Param<?> gridParam = ParamUtils.extractResponseByParamPath(submit, "/gridFiles");
		
		List<FileLineItem> gridFiles = (List<FileLineItem>) gridParam.getState();
		assertEquals(gridFiles.size(),1);
	
		
		// Invoke the download document on Grid Line Item and verify if the response is correct
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest fileDownloadRequest = MockHttpRequestBuilder.withUri(PLATFORM_ROOT)
				
				.addNested("/fileattachment/download")
				.addParam("id", uploadresponse.get(0).getId())
				.addAction(Action._get)
				.getMock();
		this.controller.handleFileDownload(response,fileDownloadRequest);
		assertEquals(response.getContentType(), fileType);
		assertEquals(response.getContentAsString(),fileContent);	
		
	}
	

}
