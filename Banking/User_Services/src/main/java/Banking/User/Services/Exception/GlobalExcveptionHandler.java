package Banking.User.Services.Exception;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Banking.User.Services.controller.AuthController;
import Banking.User.Services.payload.ApiResponse;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcveptionHandler {

   
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> hanlevalidationerror(MethodArgumentNotValidException e){
		
		List<String> collect = e.getBindingResult().getFieldErrors().stream().map(m->m.getField()+" : "+m.getDefaultMessage()).collect(Collectors.toList());	
		
		return new ResponseEntity<>(geterrorsmap(collect), HttpStatus.BAD_REQUEST);
		
	}
	
	private Map<String, List<String>> geterrorsmap(List<String> error){
		Map<String, List<String>> errorresponse=new HashMap<>();
		errorresponse.put("error", error);
		
		return errorresponse;
	}
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ee){
		  String message = ee.getMessage();
		  ApiResponse response=ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
		  return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(ResourceAlreadyFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceAlreadyFoundException ee){
		  String message = ee.getMessage();
		  ApiResponse response=ApiResponse.builder().message(message).success(true).status(HttpStatus.BAD_REQUEST).build();
		  return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
