package Transaction_Service.com.Exception;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import Transaction_Service.com.payload.ApiResponse;



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



	@ExceptionHandler(AccountNotActiveException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(AccountNotActiveException ee){
		String message = ee.getMessage();
		ApiResponse response=ApiResponse.builder().message(message).success(true).status(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(ClientException.class)
	public ResponseEntity<String> handleClientError(ClientException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}

	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<String> handleServerError(ServiceUnavailableException ex) {
		return ResponseEntity
				.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(ex.getMessage());
	}


	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ApiResponse> handleServerError(InsufficientBalanceException ex) {
		String message = ex.getMessage();
		ApiResponse response=ApiResponse.builder().message(message).success(true).status(HttpStatus.PAYMENT_REQUIRED).build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.PAYMENT_REQUIRED);
	}










}
