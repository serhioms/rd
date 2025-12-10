$("#loginButton").bind('click', function (event) {
        event.preventDefault();
		var uid = $('#Username').attr("value");
        var pwd = $('#Passwrd').attr("value");
        var tempval = $('#selectedOption').text();
		$("#Username,#Passwrd").removeClass("AddBorder"); 
		$("#errMsg").css("display", "none")
		if (tempval == "My Account - Cards" || tempval == "My Account - Line of Credit" || tempval == "Amex Online Banking") {	
			if (uid == "" && pwd == "") {
				$("#errContent").css("display", "none");
				$("#errMsg").css("display", "block");
				$("#dropDown_container").css("margin-bottom","8px");
				//$("#ssoform").css("margin-bottom","15px");	
				$("#alertMsg").html("<div><html>Both the User ID and Password are required. Please try again.</html></div>");
				if(uid == ""){$("#Username").addClass("AddBorder");}
				if(pwd == ""){$("#Passwrd").addClass("AddBorder");}
				(typeof($iTagTracker)=='function' )? $iTagTracker('siteerror','CA:Ser:Profile&Pref:enterpriselogin_en:MandatoryFieldsNotFilled') : null         
				return false;
			}
			else if (uid.length < 3 && pwd == "") {
				$("#errContent").css("display", "none");
				$("#errMsg").css("display", "block");
				$("#dropDown_container").css("margin-bottom","8px");
				//$("#ssoform").css("margin-bottom","15px");	
				$("#alertMsg").html("<div><html>The User ID or Password is incorrect. Please try again.</div></html>");
				$("#Username").addClass("AddBorder");
				$("#Passwrd").addClass("AddBorder");
				return false;
			}
			else if (uid.length < 3 && pwd != "") {
				$("#errContent").css("display", "none");
				$("#errMsg").css("display", "block");
				$("#dropDown_container").css("margin-bottom","8px");
				//$("#ssoform").css("margin-bottom","15px");	
				$("#alertMsg").html("<div><html>The User ID you entered is invalid. Please try again.</div></html>");
				$("#Username").addClass("AddBorder");
				return false;
			}
			else if (uid.length >= 3 && pwd == "") {
				$("#errContent").css("display", "none");
				$("#errMsg").css("display", "block");
				$("#dropDown_container").css("margin-bottom","8px");
				//$("#ssoform").css("margin-bottom","15px");	
				$("#alertMsg").html("<div><html>The User ID or Password is incorrect. Please try again.</div></html>"); 
				$("#Username").addClass("AddBorder");
				$("#Passwrd").addClass("AddBorder"); 
				return false;
			}
			else if (uid.length >= 3 && pwd.length < 6) {
				$("#errContent").css("display", "none");
				$("#errMsg").css("display", "block");
				$("#dropDown_container").css("margin-bottom","8px");
				//$("#ssoform").css("margin-bottom","15px");	
				$("#alertMsg").html("<div><html>The Password you entered is invalid. Please try again.</div></html>");            
				$("#Passwrd").addClass("AddBorder"); 
				return false;
			}
		}
		$("#dropDown_container").css("margin-bottom","20px");
		loginNow(tempval);
    });


function loginNow(opt) {
    var dc = window.document.ssoform;
    if (opt == "My Account - Cards" || opt == "My Account - Line of Credit" || opt == "Amex Online Banking") {
        dc.USERID.value = $("#Username").val();
        dc.PWD.value = dc.Password.value;
        dc.TARGET.value = dc.DestPage.value;
    }
    if (opt == "Online Merchant Services") {
        dc.USERID.value = $("#Username").val();
        dc.PWD.value = dc.Password.value;
        dc.TARGET.value = "https://sso.americanexpress.com/SSO/request?request_type=auth_ssopush&ssobrand=SEWOLINT&ssolang=en_CA&SSOURL=https%3A%2F%2Fwww209.americanexpress.com%2Foms%2Fglobal%2Fauthreg_home.do%3Fssolang%3Den_CA%26ssobrand%3DSEWOLINT%26mkt%3DMEX%26ssoremove%3Dyes%26ssobrand%3DSEWOLINT%26ssolang%3Den_CA";
        dc.action = "https://sso.americanexpress.com/SSO/logon.fcc";
    }
	if (opt == "American Express @ Work") {
        dc.USERID.value = $("#Username").val();
        dc.PWD.value = dc.Password.value;
        dc.TARGET.value = "https://sso.americanexpress.com/SSO/request?request_type=auth_ssopush&ssobrand=ATWORK&ssolang=en_CA&SSOURL=HTTPS%3A%2F%2Fwww140.americanexpress.com%2FATWORK%2Fen_CA%2Fatwork.do%3FpageAction%3Dinitialize%26ssoremove%3Dyes%26ssobrand%3DATWORK%26ssolang%3Den_CA";
        dc.action = "https://sso.americanexpress.com/SSO/logon.fcc";
    }
	dc.errMsgValue.value='';	
	dc.submit();
};
