package com.duha.duhabank.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class AppConfig {

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(); //Khởi tạo một Template Resolver (Bộ phân giải mẫu) dùng để xác định vị trí và cách thức tải các template. ClassLoaderTemplateResolver tìm kiếm template trong thư mục tài nguyên của ứng dụng (resource path).
	    templateResolver.setPrefix("templates/"); //Thiết lập tiền tố: Chỉ định rằng các template nằm trong thư mục templates/ (bên trong thư mục resources). Ví dụ: nếu bạn gọi template "home", nó sẽ tìm kiếm file ở templates/home...
	    templateResolver.setSuffix(".html"); //Thiết lập hậu tố: Chỉ định rằng các template có phần mở rộng là .html. Ví dụ: template "home" sẽ được tìm thấy là templates/home.html.
	    templateResolver.setCharacterEncoding("UTF-8");

	    templateEngine.setTemplateResolver(templateResolver); //Đăng ký Template Resolver đã cấu hình với Template Engine.
	    return templateEngine;
	}
	
	@Bean
	public ModelMapper modelMapperConfig() {
	    ModelMapper modelMapper = new ModelMapper();
	    modelMapper.getConfiguration()
	            .setFieldMatchingEnabled(true) //Bật chế độ ánh xạ theo tên trường (field name), không chỉ theo phương thức getter/setter. Nếu hai trường có cùng tên và kiểu dữ liệu, chúng sẽ được ánh xạ.
	            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) //Cho phép ModelMapper truy cập và ánh xạ các trường (fields) có mức độ truy cập là PRIVATE.
	            .setMatchingStrategy(MatchingStrategies.STANDARD); //Thiết lập chiến lược so khớp các trường giữa hai đối tượng là STANDARD. Chiến lược này nghiêm ngặt hơn so với LOOSE hoặc STRICT và thường là lựa chọn phổ biến.

	    return modelMapper;
	}
}
