package com.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.entities.Users;
import com.demo.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
	UsersService usrser;

	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {
		Order order=null;
		try
		{
			RazorpayClient razorpay = new RazorpayClient("rzp_test_dOfBa6zHV5jg31", "96y0BxwDs0Uw5EO2UsVSvAOU");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount",50000);
			orderRequest.put("currency","INR");
			orderRequest.put("receipt", "receipt#1");
			JSONObject notes = new JSONObject();
			notes.put("notes_key_1","Tea, Earl Grey, Hot");
			orderRequest.put("notes",notes);

			order = razorpay.orders.create(orderRequest);
		}
		catch(Exception e)
		{
			System.out.println("Exception while creating an order");
		}
		return order.toString();
		
	}
	
	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam  String orderId, @RequestParam String paymentId, @RequestParam String signature) {
	    try {
	        // Initialize Razorpay client with your API key and secret
	        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_dOfBa6zHV5jg31", "96y0BxwDs0Uw5EO2UsVSvAOU");
	        // Create a signature verification data string
	        String verificationData = orderId + "|" + paymentId;

	        // Use Razorpay's utility function to verify the signature
	        boolean isValidSignature = Utils.verifySignature(verificationData, signature, "96y0BxwDs0Uw5EO2UsVSvAOU");

	        return isValidSignature;
	    } catch (RazorpayException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	//payment-success ->Update to premium user
	@GetMapping("payment-success")
	public String paymentSucess(HttpSession session)
	{
		String email=(String) session.getAttribute("email");
		Users user=usrser.getUser(email);
		user.setPremium(true);
		usrser.updateUser(user);
		
		return "login";
	}
	
	//payment-faliure ->Redirect to login
	@GetMapping("payment-failure")
	public String paymentFailure()
	{
		//Payment Error page or Login page
		return "login";
	}
	
}
