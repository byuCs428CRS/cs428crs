var recaptchaChallenge;
var classes;

function detectIfRecaptchaIsNecessary() {
    classes = JSON.parse(Cookies.get('classes'));
    if( Cookies.get('recaptchaChallenge') === undefined || Cookies.get('recaptchaAnswer') === undefined ) {
        $.get('/public-api/recaptcha', function(data) {
            var response = JSON.parse(data);
            /*
             <div id="recaptcha_widget_div" style="" class=" recaptcha_nothad_incorrect_sol recaptcha_isnot_showing_audio"><div id="recaptcha_area"><table id="recaptcha_table" class="recaptchatable recaptcha_theme_clean"> <tbody><tr height="73"> <td class="recaptcha_image_cell" width="302"><center><div id="recaptcha_image" style="width: 300px; height: 57px;"><img id="recaptcha_challenge_image" alt="reCAPTCHA challenge image" height="57" width="300" src="https://www.google.com/recaptcha/api/image?c=03AHJ_VuuLU1GXTBy9s_OkMQk9ET0nvAQVxlGfnj2Sdk-kRpJBe1ZFYmsm0Br80A6KpPUt_VMkWzrJG8-gQOdYFpnl86Ai2-TGBncoqVOSt9wgm-dEsZGY_lNiwAGrgDpas5HBKxGT0bdW7TfJvdrT8iUy2bTAkR-GglcNyM8o_hbvPVEBoU95eOW3uG2nDNgyvHv95-O7V17RzBboPsTgbx1HblObsS2AAf6X8zj_ESBeZsQLc983hO8"></div></center></td> <td style="padding: 10px 7px 7px 7px;"> <a id="recaptcha_reload_btn" title="Get a new challenge"><img id="recaptcha_reload" width="25" height="18" alt="Get a new challenge" src="https://www.google.com/recaptcha/api/img/clean/refresh.png"></a> <a id="recaptcha_switch_audio_btn" class="recaptcha_only_if_image" title="Get an audio challenge"><img id="recaptcha_switch_audio" width="25" height="15" alt="Get an audio challenge" src="https://www.google.com/recaptcha/api/img/clean/audio.png"></a><a id="recaptcha_switch_img_btn" class="recaptcha_only_if_audio" title="Get a visual challenge"><img id="recaptcha_switch_img" width="25" height="15" alt="Get a visual challenge" src="https://www.google.com/recaptcha/api/img/clean/text.png"></a> <a id="recaptcha_whatsthis_btn" title="Help"><img id="recaptcha_whatsthis" width="25" height="16" src="https://www.google.com/recaptcha/api/img/clean/help.png" alt="Help"></a> </td> <td style="padding: 18px 7px 18px 7px;"> <img id="recaptcha_logo" alt="" width="71" height="36" src="https://www.google.com/recaptcha/api/img/clean/logo.png"> </td> </tr> <tr> <td style="padding-left: 7px;"> <div class="recaptcha_input_area" style="padding-top: 2px; padding-bottom: 7px;"> <span id="recaptcha_challenge_field_holder" style="display: none;"><input type="hidden" name="recaptcha_challenge_field" id="recaptcha_challenge_field" value="03AHJ_VuuLU1GXTBy9s_OkMQk9ET0nvAQVxlGfnj2Sdk-kRpJBe1ZFYmsm0Br80A6KpPUt_VMkWzrJG8-gQOdYFpnl86Ai2-TGBncoqVOSt9wgm-dEsZGY_lNiwAGrgDpas5HBKxGT0bdW7TfJvdrT8iUy2bTAkR-GglcNyM8o_hbvPVEBoU95eOW3uG2nDNgyvHv95-O7V17RzBboPsTgbx1HblObsS2AAf6X8zj_ESBeZsQLc983hO8"></span><input style="border: 1px solid #3c3c3c; width: 302px;" name="recaptcha_response_field" id="recaptcha_response_field" type="text" placeholder="Type the text" autocomplete="off"> </div> </td> <td colspan="2"><span id="recaptcha_privacy" class="recaptcha_only_if_privacy"><a href="http://www.google.com/intl/en/policies/" target="_blank">Privacy &amp; Terms</a></span></td> </tr> </tbody></table> </div></div>
             */

            $("#recaptcha").html(
                '<table>' +
                    '<tr>' +
                        '<td>' +
                            '<img src="data:image/jpeg;base64,'+response.image.replace('"', '&quot;')+'">' +
                        '</td>' +
                        '<td>' +
                            '<a href="javascript:detectIfRecaptchaIsNecessary()"><img src="https://www.google.com/recaptcha/api/img/clean/refresh.png" alt="Refresh"></a>' +
                            '<br><a href="javascript:recaptchaHelp()"><img width="25" height="16" src="https://www.google.com/recaptcha/api/img/clean/help.png" alt="Help"></a>' +
                        '</td>' +
                        '<td>' +
                            '<img src="https://www.google.com/recaptcha/api/img/clean/logo.png">' +
                        '</td>' +
                    '</tr>' +
                '</table>' +
                '<br><input id="recaptchaAnswer" type="text" placeholder="Type the text">'
            );
            recaptchaChallenge = response.challenge
        });
        $("#recaptcha").html('<div style="width:288px;height:45px;text-align: center;vertical-align: middle"><img src="http://jimpunk.net/Loading/wp-content/uploads/loading81.gif" width="25" height="25"></div>')
    } else {
        registerAll(false)
    }

}

function saveRecaptchaAndRegister() {
    Cookies.set('recaptchaChallenge', recaptchaChallenge)
    Cookies.set('recaptchaAnswer', document.getElementById('recaptchaAnswer').value, {expires: 1800})
    registerAll(true);
}

function registerAll(useRecaptcha) {
    $("#recaptcha").hide()
    $("#registration-submission").hide()
    $("#header-text").html('Step 3/3 : Check that all of your classes were added')
    $("#instruction-text").html("Check to see that all of your classes were registered. If something went wrong, click on the retry" +
        " button.")
    var classesHTML = ''
    for( var i=0; i<classes.length; i++ ) {
        var invisibleForm =
            '<form name="registration-form-'+i+'" method="post" id="registration-form-'+i+'" target="registration-iframe-'+i+'"' +
                'action = "https://gamma.byu.edu/ry/ae/prod/registration/cgi/regOfferings.cgi" >' +
                '<input id="c" name="c" type="hidden">' +
                '<input id="e" name="e" type="hidden">' +
                '<input id="brownie" name="brownie" type="hidden">' +
            '</form>'
        var iframe = '<iframe width="503" height="84" id="registration-iframe-'+i+'" scrolling="no"></iframe>'
        classesHTML += '<div class="center">'+classes[i].dept+' - '+classes[i].title+'</div>'+invisibleForm+iframe+'<br>'
    }
    $("#registration-result").html(classesHTML)

    for( var i=0; i<classes.length; i++ ) {
//        var iframe = document.getElementById("registration-iframe-"+i)
//        iframe.onload = function(e) {
//            iframe.height = 30
//        }
        register(useRecaptcha, classes[i], document.getElementById("registration-form-"+i))
    }

}

function register(useRecaptcha, klass, formToSubmit) {

    var brownie = ''

    brownie += 'new_year_term=' + getTerm()
    brownie += '&curr_id=' + klass.courseNumber
    brownie += '&new_title_code=' + klass.titleCode
    brownie += '&page_sequence=' + '1016452204'
    brownie += '&curr_credit=' + klass.credits
    brownie += '&section_type=' + klass.sectionType
    brownie += '&section_num=' + klass.sectionId
    if (useRecaptcha) {
        brownie += '&captcha_challenge=' + Cookies.get('recaptchaChallenge')
        brownie += '&captcha_value=' + Cookies.get('recaptchaAnswer')
    }

    formToSubmit.c.value = Cookies.get('c')
    formToSubmit.e.value = klass.e
    formToSubmit.brownie.value = brownie

    formToSubmit.submit()
}

function registrationComplete() {
    //expire all cookies except the recaptcha response
    Cookies.set('classes', 'this should expire', {expires: -1})
    Cookies.set('c', 'this should expire', {expires: -1})
    Cookies.set('e', 'this shoudl expire', {expires: -1})
}

function getTerm() {
    return '20143'
}

function recaptchaHelp() {
    var win=window.open("https://www.google.com/recaptcha/help?c="+recaptchaChallenge+"&hl=en", '_blank');
    win.focus();
}