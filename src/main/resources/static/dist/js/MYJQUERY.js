//
$body = $("body");
var interval, intervalsup, LoadSupImprimantes, EverySeconde;

$(document).on({

    ajaxStart: function () {
        if ($body.hasClass('loading')) {
            $body.removeClass('loading');
        }
        $body.addClass('loading');
    },
    ajaxStop: function () {
        $body.removeClass('loading');
    },
    ajaxError: function () {
        $body.removeClass('loading');
    },
    ajaxSuccess: function () {
        $body.removeClass('loading');
    }
});

$(document).ajaxComplete(function (event, xhr, settings) {
    $body.removeClass('loading');
    if (xhr.status == 302)
        window.location.href = "/login";
});

$("#dashboard").on("click", "#menu-dashboard", function (event) {
    ClearAllTimers();
    $('.nav-link').removeClass('active');
    $(this).find('a').addClass('active');
    $(this).parents('li').find('a').first().addClass('active');
    $("#pagecontent").empty();
    $("#pagecontent").load("/dashboard", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

$("#utilisateurs").on("click", ".menu-gestionutilisateurs", function (event) {
    ClearAllTimers();
    $('.nav-link').removeClass('active');
    $(this).find('a').addClass('active');
    $(this).parents('li').find('a').first().addClass('active');
    $("#pagecontent").empty();
    $("#pagecontent").load("/users", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

$("#utilisateurs").on("click", ".menu-profiles", function (event) {
    ClearAllTimers();
    $('.nav-link').removeClass('active');
    $(this).find('a').addClass('active');
    $(this).parents('li').find('a').first().addClass('active');
    $("#pagecontent").empty();
    $("#pagecontent").load("/profiles", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

$("#imprimantes").on("click", ".menu-gestionimprimantes", function (event) {
    ClearAllTimers();
    $('.nav-link').removeClass('active');
    $(this).find('a').addClass('active');
    $(this).parents('li').find('a').first().addClass('active');
    $("#pagecontent").empty();
    $("#pagecontent").load("/imprimantes", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

$("#imprimantes").on("click", ".menu-supervisionimprimantes", function (event) {
    ClearAllTimers();
    $('.nav-link').removeClass('active');
    $(this).find('a').addClass('active');
    $(this).parents('li').find('a').first().addClass('active');
    $("#pagecontent").empty();
    $("#pagecontent").load("/supervision", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

$("#imprimantes").on("click", ".menu-supervision", function (event) {
    ClearAllTimers();
    $('.nav-link').removeClass('active');
    $(this).find('a').addClass('active');
    $(this).parents('li').find('a').first().addClass('active');
    $("#pagecontent").empty();
    $("#pagecontent").load("/supprinters", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success") {
            LoadSupImprimantes = setInterval(function () {
                if ($('#field > div.field-item:contains("someText")').length > 0) {
                    $("#somediv").addClass("thisClass");
                }
                $('.nav-link').removeClass('active');
                $(this).find('a').addClass('active');
                $(this).parents('li').find('a').first().addClass('active');
                $("#pagecontent").empty();
                $("#pagecontent").load("/supprinters", function (responseTxt, statusTxt, xhr) {
                    if (statusTxt == "success") {
                        clearInterval(EverySeconde);
                        var compteur = 30;
                        EverySeconde = setInterval(function () {
                            compteur = compteur - 1;
                            $('#title_Sup').text("Supervision des Imprimantes (" + compteur + ")");
                        }, 1000);
                    }
                    if (statusTxt == "error")
                        alert("Error: " + xhr.status + ": " + xhr.statusText);
                });
            }, 30000);
        }

        if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

$(document).on("click", ".printerfaultydots", function (event) {
    var id = $(this).attr('id');
    $("#printerserial").val(id);

    var senddata = {
        "serial": $("#printerserial").val(),
    }
    $.ajax({
        url: "/chartgetfaultystats",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        timeout: 5000,
        //global: false,
        success: function (response) {
            var json = response;
            var data = [];
            var y = 0;
            var dataSeries = {type: "column"};
            var dataPoints = [];

            Object.keys(response)
                .sort()
                .forEach(function (v, i) {
                    console.log(v, response[v]);
                    dataPoints.push({
                        x: new Date(v),
                        y: parseInt(response[v])
                    });
                });

            dataSeries.dataPoints = dataPoints;
            data.push(dataSeries);

            var dotchart = new CanvasJS.Chart("chartgetfaultystats", {
                animationEnabled: true,
                zoomEnabled: true,
                title: {
                    text: "historique des têtes des impression"
                },
                axisX: {
                    labelFormatter: function (e) {
                        return CanvasJS.formatDate(e.value, "DD MMM YY");
                    },
                },
                axisY: {
                    valueFormatString: "#,###"
                },
                data: data
            });
            dotchart.render();
        },
        error: function (err) {
            console.log("not working. ERROR: " + JSON.stringify(err));
        }
    });
    $('#printerdotsview').modal('show');
});

$(document).on('show.bs.modal', '#printerdotsview', function () {
    console.log("SHOWNNNNNNNNNNNNNNNNNNNNNNN");

})

//***************************************************************************** USERS***********************************

$(document).on("click", "#btn-new-user", function (event) {

    $.ajax({
        url: "#",
        global: false,
        success: function (result) {
            $('#modal-users-nouveau').modal('show');
        }
    });
});

//=================== Bouton Valider de la formulaire Nouveau ===============

$(document).on('submit', '#form-users-nouveau', function (e) {
    $('#btn-users-nouveau-fermer').attr("disabled", true);
    $('#btn-users-nouveau-valider').attr("disabled", true);
    var nom = $('#text-users-nouveau-nom').val();
    var prenom = $('#text-users-nouveau-prenom').val();
    var email = $('#text-users-nouveau-email').val();
    var telephone = $('#text-users-nouveau-telephone').val();
    var profile = $('#text-users-nouveau-profile').val();
    var password1 = $('#text-users-nouveau-password1').val();
    var password2 = $('#text-users-nouveau-password2').val();

    var senddata = {
        "nom": nom,
        "prenom": prenom,
        "email": email,
        "telephone": telephone,
        "profile": profile,
        "password1": password1,
        "password2": password2,
    }

    if (password1 != password2) {
        $(".modal-title").notify(
            "les mot de passe ne sont pas identique !", "error",
            {position: "bottom center"}
        );
    }
    //This condition will only be true if each value is not an empty string
    else if (nom && prenom && email && profile && password1 && password2) {
        $.ajax({
            type: "POST",
            url: "/new_user",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            timeout: 5000,
            success: function (response) {

                if (response == '0') {
                    $(".modal-title").notify(
                        "un utilisateur avec L'adresse " + email + "\nExiste déja", "error",
                        {position: "bottom center"}
                    );

                } else if (response == '1') {

                    $(".modal-title").notify(
                        "L'utilisateur " + email + " a été ajouté avec succés", "error",
                        {position: "bottom center"}
                    );

                    $('#text-users-nouveau-nom').val("");
                    $('#text-users-nouveau-prenom').val("");
                    $('#text-users-nouveau-email').val("");
                    $('#text-users-nouveau-telephone').val("");
                    $('#text-users-nouveau-password1').val("");
                    $('#text-users-nouveau-password2').val("");

                }

            },
            error: function (err) {
                //console.log("not working. ERROR: "+JSON.stringify(err));
                $("#alert-users-ajoutmodal").html(JSON.stringify(err));
                edit("#alert-users-ajoutmodal", 5000);
            }
        });
    }
    $('#btn-users-nouveau-fermer').attr("disabled", false);
    $('#btn-users-nouveau-valider').attr("disabled", false);

    return false;
});

$(document).on('hidden.bs.modal', '#modal-users-nouveau', function () {
    $("#pagecontent").load("/users", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")
            $body.removeClass("loading");
        if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);

    });

});

//=================== Bouton Export ===============

$(document).on("click", "#btn-export-user", function () {
    var d = new Date()
    var dt = $.datepicker.formatDate('ddmmyy', d);

    excel = new ExcelGen({
        "src_id": "UserTable",
        "show_header": true,
        "file_name": "Liste Utilisateurs " + dt + "-" + d.getHours() + d.getMinutes() + d.getSeconds() + ".xlsx"
    });
    excel.generate();

    $("#btn-export-user").notify(
        "La liste a été générée !", "success",
        {position: "right"}
    );
});

//=================== Bouton Modifier ===============

$(document).on("click", ".btn-edit-user", function () {
    var id = $(this).attr('id');
    var senddata = {
        "id": id,
    }
    $.ajax({
        url: "/show_user",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        timeout: 5000,
        //global: false,
        success: function (response) {
            $('#hidded-id').val(id);
            $('#text-users-modif-nom').val(response.nom);
            $('#text-users-modif-prenom').val(response.prenom);
            $('#text-users-modif-email').val(response.email);
            $('#text-users-modif-telephone').val(response.telephone);
            $('#text-users-modif-profile').val(response.profile);
            $('#text-users-modif-profile').selectpicker('refresh');
            $('#text-users-modif-password1').val("");
            $('#text-users-modif-password2').val("");
            $('#modal-users-modif').modal('show');
        }
    });

});

$(document).on('submit', '#form-users-modif', function (e) {
    $('#btn-users-modif-fermer').attr("disabled", true);
    $('#btn-users-modif-valider').attr("disabled", true);
    var id = $('#hidded-id').val();
    var nom = $('#text-users-modif-nom').val();
    var nom = $('#text-users-modif-nom').val();
    var prenom = $('#text-users-modif-prenom').val();
    var email = $('#text-users-modif-email').val();
    var telephone = $('#text-users-modif-telephone').val();
    var profile = $('#text-users-modif-profile').val();
    var password1 = $('#text-users-modif-password1').val();
    var password2 = $('#text-users-modif-password2').val();

    var senddata = {
        "id": id,
        "nom": nom,
        "prenom": prenom,
        "email": email,
        "telephone": telephone,
        "profile": profile,
        "password1": password1,
        "password2": password2,
    }

    if (password1 != password2) {
        $(".modal-title").notify(
            "les mots de passe ne sont pas identiques !", "error",
            {position: "bottom center"}
        );
    } else if (nom && prenom && email && profile) {
        $.ajax({
            type: "POST",
            url: "/edit_user",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            timeout: 5000,
            success: function (response) {

                if (response == '0') {
                    $(".modal-title").notify(
                        "un utilisateur avec L'adresse " + email + "\nExiste déja", "error",
                        {position: "bottom center"}
                    );
                } else if (response == '2') {

                    $(".modal-title").notify(
                        "Attention : les mots de passe ne sont pas identique !", "error",
                        {position: "bottom center"}
                    );
                } else {

                    $(".modal-title").notify(
                        "L'utilisateur " + email + " est à jour", "success",
                        {position: "bottom center"}
                    );
                }

            },
            error: function (err) {
                //console.log("not working. ERROR: "+JSON.stringify(err));
                $("#alert-users-modifmodal").html(JSON.stringify(err));
                edit("#alert-users-modifmodal", 5000);
            }
        });
    }
    $('#btn-users-modif-fermer').attr("disabled", false);
    $('#btn-users-modif-valider').attr("disabled", false);

    return false;
});


$(document).on('hidden.bs.modal', '#modal-users-modif', function () {
    $("#pagecontent").load("/users", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")
            $('#hidded-id').val("");
        $('#text-users-nouveau-nom').val("");
        $('#text-users-nouveau-prenom').val("");
        $('#text-users-nouveau-email').val("");
        $('#text-users-nouveau-telephone').val("");
        $('#text-users-nouveau-password1').val("");
        $('#text-users-nouveau-password2').val("");

        if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

//=================== Bouton Supprimer ===============

$(document).on('click', '.btn-delete-user', function (event) {
    var id = $(this).attr('id');
    var email = $(this).attr('name');
    $.ajax({
        url: "#",
        global: false,
        timeout: 5000,
        success: function (result) {
            $('#hidded-id-delete').val(id);
            $('#hidded-email-delete').val(email);
            $('#text-users-modal-delete').html("<p id=\"text-users-modal-delete\">Etes-vous sûr de vouloir supprimer l'utilisateur suivant :  <b>" + email + " </b> ? </p>");
            $('#modal-users-delete').modal('show');
        }
    });
});


$(document).on('click', '#btn-users-delete-o', function (e) {
    $('#btn-users-delete-o').attr("disabled", true);
    $('#btn-users-delete-n').attr("disabled", true);
    var senddata = {
        "id": $('#hidded-id-delete').val(),
    }

    $.ajax({
        type: "POST",
        url: "/del_user",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        success: function (response) {

            $("#text-users-modal-delete").notify(
                "L'utilisateur " + $('#hidded-email-delete').val() + " a été Supprimer avec succés", "success",
                {position: "top center"}
            );

            setTimeout(function () {
                $('#modal-users-delete').modal('hide');
                $('#btn-users-delete-o').attr("disabled", false);
                $('#btn-users-delete-n').attr("disabled", false);
            }, 3000);

        },
    });

    return false;

});

$(document).on('hidden.bs.modal', '#modal-users-delete', function () {
    $("#pagecontent").load("/users", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});


//***************************************************************************** PROFILES********************************

$(document).on("click", "#btn-new-profile", function (event) {
    $.ajax({
        url: "#",
        global: false,
        success: function (result) {
            $('#modal-profiles-nouveau').modal('show');
        }
    });
});

//=================== Bouton Export ===============

$(document).on("click", "#btn-export-profile", function () {
    var d = new Date()
    var dt = $.datepicker.formatDate('ddmmyy', d);

    excel = new ExcelGen({
        "src_id": "TableProfile",
        "show_header": true,
        "file_name": "Liste Profile " + dt + "-" + d.getHours() + d.getMinutes() + d.getSeconds() + ".xlsx"
    });
    excel.generate();

    $("#btn-export-profile").notify(
        "La liste a été générée !", "success",
        {position: "right"}
    );
});

//=================== Bouton Nouveau ===============

$(document).on('submit', '#form-profiles-nouveau', function (e) {
    var jsonobj = [];
    var profile = $('#text-profiles-ajout-nameprofile').val();
    $("#TableAccess > tbody > tr").each(function () {
        var item = {};
        item["Profile"] = profile;
        item["Menu"] = $(this).find('td').eq(0).text();
        item["Consultation"] = $(this).find('td').eq(1).find(":selected").val();
        item["Ajout"] = $(this).find('td').eq(2).find(":selected").val();
        item["Modification"] = $(this).find('td').eq(3).find(":selected").val();
        item["Delete"] = $(this).find('td').eq(4).find(":selected").val();
        item["Export"] = $(this).find('td').eq(5).find(":selected").val();
        jsonobj.push(item);
    });
    $.ajax({
        type: "POST",
        url: "/new_profile",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(jsonobj),
        timeout: 5000,
        success: function (response) {

            if (response == '0') {
                $("#modal-profiles-nouveau-titre").notify(
                    "un autre profile avec le même nom que " + profile + "\nExiste déja", "error",
                    {position: "bottom center"}
                );

            } else if (response == '1') {

                $("#modal-profiles-nouveau-titre").notify(
                    "Le Profile " + profile + " a été ajouté avec succés", "success",
                    {position: "bottom center"}
                );

                $('#text-profiles-ajout-nameprofile').val("");

            }

        },
        error: function (err) {
            console.log("not working. ERROR: " + JSON.stringify(err));
        }
    });

    console.log(JSON.stringify(jsonobj));
    return false;
});

$(document).on('hidden.bs.modal', '#modal-profiles-nouveau', function () {
    $("#pagecontent").load("/profiles", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")
            $body.removeClass("loading");
        if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);

    });

});


//=================== Bouton Modifier ===================

$(document).on("click", ".btn-edit-profile", function () {
    var id = $(this).attr('id');
    $("#hidded-id-modif").val(id);
    var senddata = {
        "id": id,
    }
    $.ajax({
        url: "/show_profile",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        dataType: "json",
        timeout: 5000,
        //global: false,
        success: function (response) {
            $('#modal-profiles-modif').modal('show');
            var obj = JSON.stringify(response);
            var json = JSON.parse(obj);
            var htmltext = '';

            console.log(json);

            $.each(json, function (k, v) {
                htmltext = '<tr>';
                //$('#TableAccessmodif tr > td:contains(' + k + ')').parent().remove();
                htmltext = htmltext + '<td>' + k + '</td>';

                $.each(v, function (key, value) {

                    if (key === 'AProfile') {

                        $("#text-profiles-modif-nameprofile").val(value);

                    } else if (value === '0' && key === 'Consultation') {

                        htmltext = htmltext + '<td> \n' +
                            '           <select>  \n' +
                            '               <option value="0" selected="selected">Non</option>\n' +
                            '               <option value="1">Oui</option>\n' +
                            '           </select> \n' +
                            '       </td>';
                    } else if (value === '1' && key === 'Consultation') {
                        htmltext = htmltext + '<td> \n' +
                            '           <select>  \n' +
                            '               <option value="0">Non</option>\n' +
                            '               <option value="1" selected="selected">Oui</option>\n' +
                            '           </select> \n' +
                            '       </td>';
                    } else if (value === '0' && key === 'DAjout') {
                        htmltext = htmltext + '<td> <select>  \n' +
                            '           <option value="0" selected="selected">Non</option>\n' +
                            '           <option value="1">Oui</option>\n' +
                            '      </select></td>';
                    } else if (value === '1' && key === 'DAjout') {
                        htmltext = htmltext + '<td> <select> \n' +
                            '           <option value="0">Non</option>\n' +
                            '           <option value="1" selected="selected">Oui</option>\n' +
                            '      </select> </td>';
                    } else if (value === '0' && key === 'EModification') {
                        htmltext = htmltext + '<td> <select>  \n' +
                            '           <option value="0" selected="selected">Non</option>\n' +
                            '           <option value="1">Oui</option>\n' +
                            '      </select></td>';
                    } else if (value === '1' && key === 'EModification') {
                        htmltext = htmltext + '<td> <select> \n' +
                            '           <option value="0">Non</option>\n' +
                            '           <option value="1" selected="selected">Oui</option>\n' +
                            '      </select> </td>';
                    } else if (value === '0' && key === 'FSuppression') {
                        htmltext = htmltext + '<td> <select>  \n' +
                            '           <option value="0" selected="selected">Non</option>\n' +
                            '           <option value="1">Oui</option>\n' +
                            '      </select></td>';
                    } else if (value === '1' && key === 'FSuppression') {
                        htmltext = htmltext + '<td> <select> \n' +
                            '           <option value="0">Non</option>\n' +
                            '           <option value="1" selected="selected">Oui</option>\n' +
                            '      </select> </td>';
                    } else if (value === '0' && key === 'GExportation') {
                        htmltext = htmltext + '<td> <select>  \n' +
                            '           <option value="0" selected="selected">Non</option>\n' +
                            '           <option value="1">Oui</option>\n' +
                            '      </select></td>';
                    } else if (value === '1' && key === 'GExportation') {
                        htmltext = htmltext + '<td> <select> \n' +
                            '           <option value="0">Non</option>\n' +
                            '           <option value="1" selected="selected">Oui</option>\n' +
                            '      </select> </td>';
                    }
                });

                $('#TableAccessmodif > tbody').append(htmltext + '</tr>');
            });
        }
    });

});


$(document).on('submit', '#form-profiles-modif', function (e) {
    jsonobj = [];
    $("#TableAccessmodif > tbody > tr").each(function () {
        var item = {};
        item["id"] = $("#hidded-id-modif").val();
        item["Profile"] = $('#text-profiles-modif-nameprofile').val();
        item["Menu"] = $(this).find('td').eq(0).text();
        item["Consultation"] = $(this).find('td').eq(1).find(":selected").val();
        item["Ajout"] = $(this).find('td').eq(2).find(":selected").val();
        item["Modification"] = $(this).find('td').eq(3).find(":selected").val();
        item["Suppression"] = $(this).find('td').eq(4).find(":selected").val();
        item["Exportation"] = $(this).find('td').eq(5).find(":selected").val();
        jsonobj.push(item);
    });
    $.ajax({
        type: "POST",
        url: "/edit_profile",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(jsonobj),
        timeout: 5000,
        success: function (response) {

            if (response == '0') {
                $(".modal-title").notify(
                    "un autre profile avec le même nom que " + $('#text-profiles-modif-nameprofile').val() + "\nExiste déja", "error",
                    {position: "bottom center"}
                );

            } else if (response == '1') {

                $(".modal-title").notify(
                    "Le Profile " + $('#text-profiles-modif-nameprofile').val() + " a été ajouté avec succés", "success",
                    {position: "bottom center"}
                );

                $('#text-profiles-ajout-nameprofile').val("");

            } else if (response == '2') {

                $(".modal-title").notify(
                    "Erreur inconnu, contacté votre développeur", "error",
                    {position: "bottom center"}
                );

            }

        },
        error: function (err) {
            //console.log("not working. ERROR: "+JSON.stringify(err));
            $("#alert-users-ajoutmodal").html(JSON.stringify(err));
            edit("#alert-users-ajoutmodal", 5000);
        }
    });

    console.log(JSON.stringify(jsonobj));
    return false;
});

$(document).on('hidden.bs.modal', '#modal-profiles-modif', function () {
    $("#pagecontent").load("/profiles", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});


//=================== Bouton Supprimer ===============

$(document).on('click', '.btn-delete-profile', function (event) {
    var id = $(this).attr('id');
    var profile = $(this).attr('name');
    $.ajax({
        url: "#",
        global: false,
        timeout: 5000,
        success: function (result) {
            $('#hidded-id-delete').val(id);
            $('#hidded-profile-delete').val(profile);
            $('#text-profiles-modal-delete').html("<p id=\"text-profiles-modal-delete\">Etes-vous sûr de vouloir supprimer le profile suivant :  <b>" + profile + " </b> ? </p>");
            $('#modal-profiles-delete').modal('show');
        }
    });
});


$(document).on('click', '#btn-profiles-delete-o', function (e) {
    $('#btn-profiles-delete-o').attr("disabled", true);
    $('#btn-profiles-delete-n').attr("disabled", true);
    var senddata = {
        "id": $('#hidded-id-delete').val(),
    }

    $.ajax({
        type: "POST",
        url: "/del_profile",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        success: function (response) {

            $("#text-profiles-modal-delete").notify(
                "L'utilisateur " + $('#hidded-profile-delete').val() + " a été Supprimer avec succés", "success",
                {position: "top center"}
            );

            setTimeout(function () {
                $('#modal-profiles-delete').modal('hide');
                $('#btn-profiles-delete-o').attr("disabled", false);
                $('#btn-profiles-delete-n').attr("disabled", false);
            }, 3000);

        },
    });

    return false;

});

$(document).on('hidden.bs.modal', '#modal-profiles-delete', function () {
    $("#pagecontent").load("/profiles", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});


//***************************************************************************** IMPRIMANTES ***********************************

$(document).on("click", "#btn-new-printer", function (event) {
    $.ajax({
        url: "#",
        global: false,
        success: function (result) {
            $('#text-printers-nouveau-user').val("");
            $('#text-printers-nouveau-password').val("");
            $('#modal-printers-nouveau').modal('show');
        }
    });
});

//=================== Bouton Export ===============

$(document).on("click", "#btn-export-printer", function () {
    var d = new Date()
    var dt = $.datepicker.formatDate('ddmmyy', d);

    excel = new ExcelGen({
        "src_id": "printerTable",
        "show_header": true,
        "file_name": "Liste Imprimantes " + dt + "-" + d.getHours() + d.getMinutes() + d.getSeconds() + ".xlsx"
    });
    excel.generate();

    $("#btn-export-profile").notify(
        "La liste a été générée !", "success",
        {position: "right"}
    );
});

//=================== Bouton Valider de la formulaire Nouveau ===============

$(document).on('submit', '#form-printers-nouveau', function (e) {
    $('#btn-printers-nouveau-fermer').attr("disabled", true);
    $('#btn-printers-nouveau-valider').attr("disabled", true);
    var marque = $('#text-printers-nouveau-marque option:selected').text();
    var modele = $('#text-printers-nouveau-modele option:selected').text();
    var ip = $('#text-printers-nouveau-ip').val();
    var type = $('#text-printers-nouveau-type option:selected').text();
    var user = $('#text-printers-nouveau-user').val();
    var password = $('#text-printers-nouveau-password').val();
    var ligne = $('#text-printers-nouveau-ligne').val();
    var description = $('#text-printers-nouveau-description').val();

    var senddata = {
        "marque": marque,
        "modele": modele,
        "ip": ip,
        "type": type,
        "user": user,
        "password": password,
        "ligne": ligne,
        "description": description,
    }

    //This condition will only be true if each value is not an empty string
    if (marque && modele && ip && type && ligne) {
        $.ajax({
            type: "POST",
            url: "/new_printer",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            timeout: 5000,
            success: function (response) {

                if (response == '0') {
                    $(".modal-title").notify(
                        "l'imprimante avec L'adresse ip " + ip + " Existe déja", "error",
                        {position: "bottom center"}
                    );
                } else if (response == '1') {

                    $(".modal-title").notify(
                        "L'imprimante " + type + " " + ip + " a été ajouté avec succés", "success",
                        {position: "bottom center"}
                    );

                    $('#text-printers-nouveau-ip').val("");
                    $('#text-printers-nouveau-user').val("");
                    $('#text-printers-nouveau-password').val("");
                    $('#text-printers-nouveau-description').val("");

                }

            },
            error: function (err) {
                //console.log("not working. ERROR: "+JSON.stringify(err));
                $("#alert-printers-ajoutmodal").html(JSON.stringify(err));
                edit("#alert-printers-ajoutmodal", 5000);
            }
        });
    }
    $('#btn-printers-nouveau-fermer').attr("disabled", false);
    $('#btn-printers-nouveau-valider').attr("disabled", false);

    return false;
});

$(document).on('hidden.bs.modal', '#modal-printers-nouveau', function () {
    $("#pagecontent").load("/imprimantes", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")
            $body.removeClass("loading");
        if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);

    });

});


//=================== Bouton Modifier ===============

$(document).on("click", ".btn-edit-printer", function () {
    var id = $(this).attr('id');
    $('#text-printers-modif-ip').val("");
    $('#text-printers-modif-user').val("");
    $('#text-printers-modif-password').val("");
    $('#text-printers-modif-description').val("");
    var senddata = {
        "id": id,
    }
    $.ajax({
        url: "/show_printer",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        timeout: 5000,
        //global: false,
        success: function (response) {
            $('#hidded-id').val(id);
            $('#text-printers-modif-marque').val(response.marque);
            $('#text-printers-modif-marque').selectpicker('refresh');
            $('#text-printers-modif-modele').val(response.modele);
            $('#text-printers-modif-modele').selectpicker('refresh');
            $('#text-printers-modif-ip').val(response.ip);
            $('#text-printers-modif-type').val(response.type);
            $('#text-printers-modif-type').selectpicker('refresh');
            $('#text-printers-modif-user').val(response.user);
            $('#text-printers-modif-password').val(response.password);
            $('#text-printers-modif-ligne').val(response.ligne);
            $('#text-printers-modif-ligne').selectpicker('refresh');
            $('#text-printers-modif-description').val(response.description);
            $('#modal-printers-modif').modal('show');
        }
    });
});


$(document).on('submit', '#form-printers-modif', function (e) {
    $('#btn-printers-modif-fermer').attr("disabled", true);
    $('#btn-printers-modif-valider').attr("disabled", true);
    var id = $('#hidded-id').val();
    var marque = $('#text-printers-modif-marque option:selected').text();
    var modele = $('#text-printers-modif-modele option:selected').text();
    var ip = $('#text-printers-modif-ip').val();
    var type = $('#text-printers-modif-type option:selected').text();
    var user = $('#text-printers-modif-user').val();
    var password = $('#text-printers-modif-password').val();
    var ligne = $('#text-printers-modif-ligne').val();
    var description = $('#text-printers-modif-description').val();

    var senddata = {
        "id": id,
        "marque": marque,
        "modele": modele,
        "ip": ip,
        "type": type,
        "user": user,
        "password": password,
        "ligne": ligne,
        "description": description,
    }

    if (marque && modele && ip && type && ligne) {
        $.ajax({
            type: "POST",
            url: "/edit_printer",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            timeout: 5000,
            success: function (response) {

                if (response == '0') {
                    $(".modal-title").notify(
                        "l'imprimante avec L'adresse ip " + ip + " Existe déja", "error",
                        {position: "bottom center"}
                    );
                } else {
                    $(".modal-title").notify(
                        "L'imprimante " + type + " " + ip + " a été modifié avec succés", "success",
                        {position: "bottom center"}
                    );
                }

            },
            error: function (err) {
                //console.log("not working. ERROR: "+JSON.stringify(err));
                $("#alert-printers-modifmodal").html(JSON.stringify(err));
                edit("#alert-printers-modifmodal", 5000);
            }
        });
    }
    $('#btn-printers-modif-fermer').attr("disabled", false);
    $('#btn-printers-modif-valider').attr("disabled", false);

    return false;
});

$(document).on('hidden.bs.modal', '#modal-printers-modif', function () {
    $("#pagecontent").load("/imprimantes", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")
            $body.removeClass("loading");
        if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);

    });
});


//=================== Bouton Supprimer ===============

$(document).on('click', '.btn-delete-printer', function (event) {
    var id = $(this).attr('id');
    var ip = $(this).attr('name');
    $.ajax({
        url: "#",
        global: false,
        timeout: 5000,
        success: function (result) {
            $('#hidded-id-delete').val(id);
            $('#hidded-ip-delete').val(ip);
            $('#text-printers-modal-delete').html("<p id=\"text-users-modal-delete\">Etes-vous sûr de vouloir supprimer l'imprimante suivante :  <b>" + ip + " </b> ? </p>");
            $('#modal-printers-delete').modal('show');
        }
    });
});


$(document).on('click', '#btn-printers-delete-o', function (e) {
    $('#btn-printers-delete-o').attr("disabled", true);
    $('#btn-printers-delete-n').attr("disabled", true);
    var senddata = {
        "id": $('#hidded-id-delete').val(),
    }

    $.ajax({
        type: "POST",
        url: "/del_printer",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        success: function (response) {

            $("#text-printers-modal-delete").notify(
                "L'utilisateur " + $('#hidded-ip-delete').val() + " a été Supprimer avec succés", "success",
                {position: "top center"}
            );

            setTimeout(function () {
                $('#modal-printers-delete').modal('hide');
                $('#btn-printers-delete-o').attr("disabled", false);
                $('#btn-printers-delete-n').attr("disabled", false);
            }, 3000);

        },
    });

    return false;

});

$(document).on('hidden.bs.modal', '#modal-printers-delete', function () {
    $("#pagecontent").load("/imprimantes", function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success")

            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
});

//==================== Menu Table imprimante ==============

$("body").on('contextmenu', '#printerTable td', function (e) {
    $('tr').css('background', 'none');
    var top = e.pageY;
    var left = e.pageX;
    $(this).closest('tr').css('background', '#A3EBB1');
    console.log($(this).closest('tr').find('td:eq(0)').text());
    $("#menu").css({
        display: "block",
        top: top,
        left: left
    });
    return false; //blocks default Webbrowser right click menu
});

$("body").on("click", function () {
    if ($("#menu").css('display') == 'block') {
        $(" #menu ").hide();
    }
    $('tr').css('background', 'none');
});

$("#menu a").on("click", function () {
    $(this).parent().hide();
});


//********************************************************************** SUPERVISION ***********************************
$('body').on('show.bs.modal', '#modal-supervision', function () {
    $("#image-ip-state-scanedip").hide();
    var desiredOption = $("#text-supervision-ligne").text();
    if (desiredOption = !'') {
        UpdateLigneListe();
    }

    ClearAllTimers();

    interval = setInterval(function () {
        var ip = $('#scannedipadresse').val();
        checkping($("#text-supervision-type").find("option:selected").val());

    }, 3000);
});

$('body').on('hide.bs.modal', '#modal-supervision', function () {
    ClearAllTimers();
    $('#modal-supervision').hide();

    intervalsup = setInterval(function () {
        if (ip = !'')
            checkpingsup($('#scannedipadresse').val());

    }, 3000);
});

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$('body').on('change', 'select[id="text-supervision-type"]', function () {
    setTimeout(checkping($("#text-supervision-type").find("option:selected").val()), 500);
});

$('body').on('change', 'select[id="text-supervision-ligne"]', function () {
    UpdateLigneListe();
});

function checkpingsup(ipadresstoping) {
    var ip = ipadresstoping;
    console.log(ip);
    var senddata = {
        "ip": ip,
    }
    if (ip == '' || ip == null) {
        $('#image-ip-state-scanedip').attr("src", "/dist/img/redball.png");
    } else {
        $.ajax({
            type: "POST",
            url: "/check_ping",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            global: false,
            success: function (response) {
                console.log(response);
                if (response == 1) {
                    $("#image-ip-state-scanedip").attr("src", "/dist/img/greenball.png");
                } else {
                    $("#image-ip-state-scanedip").attr("src", "/dist/img/redball.png");
                }
            },

            error: function (err) {
                $("#image-ip-state-scanedip").notify(
                    "Impossible de vérifier l'état de l'imprimante", "error",
                    {position: "top center"}
                );
            }
        });
    }
    return false;
}

function checkping(ipadresstoping) {
    var ip = ipadresstoping;
    console.log(ip);
    var senddata = {
        "ip": ip,
    }
    if (ip == '' || ip == null) {
        $('#image-ip-state-supervision').attr("src", "/dist/img/redball.png");
        $('#pingstate').val("0");
    } else {
        $.ajax({
            type: "POST",
            url: "/check_ping",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            global: false,
            success: function (response) {
                console.log(response);
                if (response == 1) {
                    $("#image-ip-state-supervision").attr("src", "/dist/img/greenball.png");
                    $('#pingstate').val("1");
                } else {
                    $("#image-ip-state-supervision").attr("src", "/dist/img/redball.png");
                    $('#pingstate').val("0");
                }
            },

            error: function (err) {
                $('#pingstate').val("0");
                $("#image-ip-state-supervision").notify(
                    "Impossible de vérifier l'état de l'imprimante", "error",
                    {position: "top center"}
                );
            }
        });
    }
    return false;
}

function UpdateLigneListe() {
    var senddata = {
        "id": $('#text-supervision-ligne').val(),
    }

    $.ajax({
        type: "POST",
        url: "/get_printers",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        success: function (response) {
            $('#btn-supervision-valider').attr("disabled", true);
            var obj = JSON.stringify(response);
            var json = JSON.parse(obj);
            var count = 0;
            console.log(json);

            $("#text-supervision-type").html('');
            $("#text-supervision-type").selectpicker('refresh');
            $.each(json, function (k, v) {
                var htm = '<option value="ip">type | ip</option>';
                $.each(v, function (key, value) {
                    if (key == "type") {
                        htm = htm.replace('type', value);
                    }
                    if (key == "ip") {
                        htm = htm.replace('ip', value);
                        htm = htm.replace('ip', value);
                    }
                });
                $("#text-supervision-type").append(htm);
                $("#text-supervision-type").selectpicker('refresh');
                count++;
            });

            if (count == 0) {
                $("#text-supervision-type").notify(
                    "Aucune imprimante dans cette ligne", "warning",
                    {position: "top center"}
                );
                $("#text-supervision-type").html('');
                $("#text-supervision-type").selectpicker('refresh');
            } else {
                $('#btn-supervision-valider').attr("disabled", false);
                $(this).val($("#text-supervision-ligne option:first").val());
                $("#text-supervision-type").selectpicker('refresh');
                setTimeout(checkping($("#text-supervision-type").find("option:selected").val()), 500);
            }
        },
        error: function (err) {
            $("#text-supervision-type").html('');
            $("#text-supervision-type").selectpicker('refresh');
            $("#text-supervision-type").notify(
                "Impossible de contacter le serveur", "error",
                {position: "top center"}
            );
        }
    });

    return false;
}

// =================================== Button Valider Modal ======

$(document).on('submit', '#form-supervision', function (e) {

    if ($('#pingstate').val() == "0") {
        $("#image-ip-state-supervision").notify(
            "Impossible de vérifier l'état de l'imprimante", "error",
            {position: "top center"}
        );
    } else {
        var ip = $('#text-supervision-type').val();
        $('#modal-supervision').modal('hide');

        var senddata = {
            "ip": ip,
        }

        $.ajax({
            type: "POST",
            //dataType: "json",
            url: "/get_Json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(senddata),
            timeout: 20000,
            success: function (response) {
                var json = JSON.stringify(response);
                var json = JSON.parse(json);

                var totalram = json.totalram;
                var totalstorage = json.totalstorage;
                var freestorage = json.freestorage;
                var freeram = json.freeram;
                var usedstorage = json.usedstorage;
                var usedram = json.usedram;
                var percentusedstorage;
                var percentusedram
                //console.log(json);

                if (json.totalram = !'') {
                    totalram = parseInt(totalram.replace(' bytes', '').toString()) / 1024 / 1024;
                }
                if (json.totalstorage = !'') {
                    console.log(totalstorage.replace(" bytes", ""));
                    totalstorage = parseInt(totalstorage.replace(' bytes', '').toString()) / 1024 / 1024;
                }
                if (json.freestorage = !'') {
                    freestorage = parseInt(freestorage.replace(' bytes', '').toString()) / 1024 / 1024;
                    usedstorage = totalstorage - freestorage;
                    percentusedstorage = (totalstorage / freestorage) * 100;
                }
                if (json.freeram = !'') {
                    freeram = parseInt(freeram.replace(' bytes', '').toString()) / 1024 / 1024;
                    usedram = totalram - freeram;
                    percentusedram = (freeram / totalram) * 100;
                }
                $("#image-ip-state-scanedip").show();
                $('#scannedipadresse').val(json.ipadresse);
                $('#scannedmarque').val(json.modeleprinter);
                $('#h1-supervision').html('Supervision <a >(IP: ' + json.ipadresse + ' | S/N: ' + json.serialnumber + ')</a>');
                $('#serialnumber').html(json.serialnumber);
                $('#printeruptime').html(json.printeruptime);
                $('#modeleprinter').html(json.modeleprinter);
                $('#configurationnumber').html(json.configurationnumber);
                $('#firmwareversion').html(json.firmwareversion);
                $('#ipadresse').html('<img id="image-ip-state-scanedip" height="24px" width="24px" src="" alt=""/> <a href="http://' + json.ipadresse + '" target="_blank">' + json.ipadresse + '</a>');
                $('#totalstorage').html(totalstorage.toFixed(2) + " MB");
                $('#freestorage').html(freestorage.toFixed(2) + " MB");
                console.log(json.cpuusage.replace("%", ""));
                $('#totalram').html(totalram.toFixed(2) + " MB");
                $('#freeram').html(freeram.toFixed(2) + " MB");
                $('#usedram').html(usedram.toFixed(2) + " MB");
                $('#printedlabels').html(json.printedlabels);
                $('#totaldistanceprint').html(json.totaldistanceprint);
                $('#totaldistanceprinthead').html(json.totaldistanceprinthead);
                $('#minimumtemperature').html(json.minimumtemperature);
                $('#faultydots').html(json.faultydots);
                $('#ribbondistance').html(json.ribbondistance);
                $('#ethernetmac').html(json.ethernetmac);
                //$('#printmethodribbon').html(json.printmethodribbon);
                $('#mediatype').html(json.mediatype);
                $('#mediawidth').html(json.mediawidth);
                $('#medialenght').html(json.medialenght);
                $('#commandlanguage').html(json.commandlanguage);
                $('#printmethodribbon').html(json.printmethodribbon);
                $('#mediatype').html(json.mediatype);
                $('#marjinx').html(json.marjinx);
                $('#mediawidth').html(json.mediawidth);
                $('#medialenght').html(json.medialenght);


                if (json.cpuusage.replace("%", "") > 90)
                    $('#cpuusage').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-danger" role="progressbar" style="width: ' + json.cpuusage + '" aria-valuenow="' + json.cpuusage.replace("%", "") + '" aria-valuemin="0" aria-valuemax="100">' + json.cpuusage + '</div></div>');
                else if (json.cpuusage.replace("%", "") > 70)
                    $('#cpuusage').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-warning" role="progressbar" style="width: ' + json.cpuusage + '" aria-valuenow="' + json.cpuusage.replace("%", "") + '" aria-valuemin="0" aria-valuemax="100">' + json.cpuusage + '</div></div>');
                else if (json.cpuusage.replace("%", "") > 0)
                    $('#cpuusage').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-success" role="progressbar" style="width: ' + json.cpuusage + '" aria-valuenow="' + json.cpuusage.replace("%", "") + '" aria-valuemin="0" aria-valuemax="100">' + json.cpuusage + '</div></div>');


                if (percentusedstorage > 90)
                    $('#usedstorage').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-danger" role="progressbar" style="width: ' + percentusedstorage + '" aria-valuenow="' + percentusedstorage.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100">' + usedstorage.toFixed(2) + ' MB</div></div>');
                if (percentusedstorage > 70)
                    $('#usedstorage').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-warning" role="progressbar" style="width: ' + percentusedstorage + '" aria-valuenow="' + percentusedstorage.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100">' + usedstorage.toFixed(2) + ' MB</div></div>');
                if (percentusedstorage > 0)
                    $('#usedstorage').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-success" role="progressbar" style="width: ' + percentusedstorage + '" aria-valuenow="' + percentusedstorage.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100">' + usedstorage.toFixed(2) + ' MB</div></div>');

                if (percentusedram > 90)
                    $('#usedram').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-danger" role="progressbar" style="width: ' + percentusedram + '" aria-valuenow="' + percentusedram.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100">' + percentusedram.toFixed(2) + ' MB</div></div>');
                if (percentusedram > 70)
                    $('#usedram').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-warning" role="progressbar" style="width: ' + percentusedram + '" aria-valuenow="' + percentusedram.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100">' + percentusedram.toFixed(2) + ' MB</div></div>');
                if (percentusedram > 0)
                    $('#usedram').html('<div class="progress" style="height: 20px"><div class="progress-bar bg-success" role="progressbar" style="width: ' + percentusedram + '" aria-valuenow="' + percentusedram.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100">' + percentusedram.toFixed(2) + ' MB</div></div>');


                if (json.printstatut == 'Ready') {
                    $('#printstatut').html('<img src="/dist/img/ready.png" alt="ready" height="16" width="16"> Ready');
                } else {
                    $('#printstatut').html('<img src="/dist/img/error.png" alt="ready" height="16" width="16"> ' + json.printstatut);
                }

            },
            error: function (err) {
                //console.log("not working. ERROR: "+JSON.stringify(err));
                $('#modal-supervision').modal('show');
            }
        });
    }

    return false;
});

$(document).on('click', '#btn-new-supervision', function (e) {
    $('#modal-supervision').modal('show');
});

$(document).on('click', '#btn-image-supervision', function (e) {
    $('#imagepreview-supervision').attr('src', 'http://' + $('#scannedipadresse').val() + '/printer/label.png');
    $('#modal-image-supervision').modal('show');
});


$(document).on('click', '#btn-reboot-supervision', function (e) {

    var ip = $('#text-supervision-type').val();
    var model = $('#scannedmarque').val();

    var senddata = {
        "ip": ip,
        "model": model,
    }

    $.ajax({
        type: "POST",
        url: "/reboot_printer",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        success: function (response) {

            if (response == 0) {
                $("#btn-reboot-supervision").notify(
                    "Impossible de redémarrer l'imprimante : " + ip, "error",
                );
            } else {
                $("#btn-reboot-supervision").notify(
                    "Vous avez bien redémarrer l'imprimante : " + ip, "success",
                );
            }


        },

        error: function (err) {
            $("#btn-reboot-supervision").notify(
                "Impossible de redémarrer cette imprimante ! vérifier le status de ping", "error",
            );
        }
    });

    return false;

});


//======================================== MENU SUPERVISION PRINTERS =============================================================================================

$(document).on('click', '.btn-reboot-sup', function (event) {
    var id = $(this).attr('id');
    var model = $(this).attr('name');

    var senddata = {
        "id": id,
        "model": model,
    }
    console.log(id);
    console.log(model);

    $.ajax({
        type: "POST",
        url: "/reboot_printer",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(senddata),
        timeout: 5000,
        success: function (response) {
            if (response == 0) {
                $.notify(
                    "Impossible de redémarrer l'imprimante : " + id, "error",
                );
            } else {
                $.notify(
                    "Vous avez bien redémarrer l'imprimante : " + id, "success",
                );
            }
        },
        error: function (err) {
            $.notify(
                "Impossible de redémarrer cette imprimante ! vérifier le statut de l'imprimante", "error",
            );
        }
    });
});


//============================CLASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS====================================================================================================================================

function ClearAllTimers() {
    clearInterval(LoadSupImprimantes);
    clearInterval(EverySeconde);
    clearInterval(intervalsup);
    clearInterval(interval);
}

//=========================================================================================================================================================================

function edit(id, timer) {
    $(id).show();
    setTimeout(function () {
        $(id).hide();
    }, timer);
}

function num(id, menu) {
    if (menu = "utilisateurs")
        return "U" + id.pad(4);
    else if (menu = "clients")
        return "CL" + id.pad(5);
    else if (menu = "categories")
        return "C" + id.pad(5);
    else if (menu = "profiles")
        return "P" + id.pad(3);
    else (menu = "articles")
    return "A" + id.pad(6);
}

var dateFormat = function () {
    var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
        timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
        timezoneClip = /[^-+\dA-Z]/g,
        pad = function (val, len) {
            val = String(val);
            len = len || 2;
            while (val.length < len) val = "0" + val;
            return val;
        };

    // Regexes and supporting functions are cached through closure
    return function (date, mask, utc) {
        var dF = dateFormat;

        // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
        if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
            mask = date;
            date = undefined;
        }

        // Passing date through Date applies Date.parse, if necessary
        date = date ? new Date(date) : new Date;
        if (isNaN(date)) throw SyntaxError("invalid date");

        mask = String(dF.masks[mask] || mask || dF.masks["default"]);

        // Allow setting the utc argument via the mask
        if (mask.slice(0, 4) == "UTC:") {
            mask = mask.slice(4);
            utc = true;
        }

        var _ = utc ? "getUTC" : "get",
            d = date[_ + "Date"](),
            D = date[_ + "Day"](),
            m = date[_ + "Month"](),
            y = date[_ + "FullYear"](),
            H = date[_ + "Hours"](),
            M = date[_ + "Minutes"](),
            s = date[_ + "Seconds"](),
            L = date[_ + "Milliseconds"](),
            o = utc ? 0 : date.getTimezoneOffset(),
            flags = {
                d: d,
                dd: pad(d),
                ddd: dF.i18n.dayNames[D],
                dddd: dF.i18n.dayNames[D + 7],
                m: m + 1,
                mm: pad(m + 1),
                mmm: dF.i18n.monthNames[m],
                mmmm: dF.i18n.monthNames[m + 12],
                yy: String(y).slice(2),
                yyyy: y,
                h: H % 12 || 12,
                hh: pad(H % 12 || 12),
                H: H,
                HH: pad(H),
                M: M,
                MM: pad(M),
                s: s,
                ss: pad(s),
                l: pad(L, 3),
                L: pad(L > 99 ? Math.round(L / 10) : L),
                t: H < 12 ? "a" : "p",
                tt: H < 12 ? "am" : "pm",
                T: H < 12 ? "A" : "P",
                TT: H < 12 ? "AM" : "PM",
                Z: utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                o: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                S: ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
            };

        return mask.replace(token, function ($0) {
            return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
        });
    };
}();

// Some common format strings
dateFormat.masks = {
    "default": "ddd mmm dd yyyy HH:MM:ss",
    shortDate: "m/d/yy",
    mediumDate: "mmm d, yyyy",
    longDate: "mmmm d, yyyy",
    fullDate: "dddd, mmmm d, yyyy",
    shortTime: "h:MM TT",
    mediumTime: "h:MM:ss TT",
    longTime: "h:MM:ss TT Z",
    isoDate: "yyyy-mm-dd",
    isoTime: "HH:MM:ss",
    isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
    dayNames: [
        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    ],
    monthNames: [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    ]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
    return dateFormat(this, mask, utc);
};