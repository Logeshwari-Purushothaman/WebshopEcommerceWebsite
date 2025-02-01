package com.example.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ReviewController {

    @Autowired
    private ProductDetailFacade productDetailFacade;

    // Existing POST method (you might want to keep this for non-WebSocket submissions)
    @PostMapping("/review")
    public String submitReview(@RequestParam Long productId,
                               @RequestParam String productName,
                               @RequestParam String userName,
                               @RequestParam String reviewText,
                               Model model) {
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        
        ReviewModel review = new ReviewModel(productId, productName, userName, reviewText, formattedDate);
        
        model.addAttribute("review", review);
        
        // Fetch product details and add to model
        ProductDetailDTO productDetailDTO = productDetailFacade.getProductDetailDTO(productId);
        model.addAttribute("productDetailDTO", productDetailDTO);
        
        return "productDetail";
    }

    // New WebSocket method
    @MessageMapping("/review")
    @SendTo("/topic/reviews")
    public ReviewModel handleReview(ReviewModel review) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        
        review.setDate(formattedDate);
        
        // to fetch product details, we can do it here
        // ProductDetailDTO productDetailDTO = productDetailFacade.getProductDetailDTO(review.getProductId());
        // we might want to include this information in the review or handle it separately
        
        return review;
    }
}
