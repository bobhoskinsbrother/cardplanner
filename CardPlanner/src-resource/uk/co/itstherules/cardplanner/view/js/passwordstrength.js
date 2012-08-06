var PasswordStrength = { 
	check: function (target, name)  {
		var score = PasswordStrength.test($(target).value);
		var percent = (score / 50) * 100;
		var targetElement = $(name + '_bar');
		
		targetElement.removeClassName('pws_empty');
		targetElement.removeClassName('pws_bad');
		targetElement.removeClassName('pws_weakest');
		targetElement.removeClassName('pws_weak');
		targetElement.removeClassName('pws_medium');
		targetElement.removeClassName('pws_strong');
		targetElement.removeClassName('pws_strongest');
		
		if(score == 0) { targetElement.addClassName('pws_empty'); }
		else if(score < 10) { targetElement.addClassName('pws_bad'); }
		else if(score > 10 && score < 16) { targetElement.addClassName('pws_weakest'); }
		else if (score > 15 && score < 25) { targetElement.addClassName('pws_weak'); }
		else if (score > 24 && score < 35) { targetElement.addClassName('pws_medium'); }
		else if (score > 34 && score < 45) { targetElement.addClassName('pws_strong'); }
		else { targetElement.addClassName('pws_strongest'); }
	},
	
	test: function(password) {
		var score = 0;
		
		if (password.length > 0 && password.length < 5) { score = score + 3; } // length 4 or less 
		else if (password.length > 4 && password.length < 8) { score = score + 6; } // length between 5 and 7
		else if (password.length > 7 && password.length < 16) { score = score + 12; } // length between 8 and 15
		else if (password.length > 15) { score = score + 18; } // length 16 or more
		
		if (password.match(/[a-z]/)) { score = score + 1; } // at least one lower case letter
		if (password.match(/[A-Z]/)) { score = score + 5; } // at least one upper case letter
		if (password.match(/\d+/)) { score =  score + 5; } // at least one number
		if (password.match(/(.*[0-9].*[0-9].*[0-9])/)) { score = score + 5; } // at least three numbers
		if (password.match(/.[!,@,#,$,%,^,&,*,?,_,~]/)) { score = score + 5; } // at least one special character
		if (password.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/)) { score = score + 5; } // at least two special characters
		if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) { score = score + 2; } // upper and lower case
		if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) { score = score + 2; } // letters and numbers
		if (password.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)) { score = score + 2; } // letters, numbers, and special characters
	
		return score;
	}
}