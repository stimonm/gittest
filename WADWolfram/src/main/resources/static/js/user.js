function changeShow(element) {
    var elementID = $(element).attr("id");
    switch (elementID) {
    case 'myProyects':
        if ($('#userProjects').hasClass('hide')) {
            $('#userProjects').removeClass('hide');
            $('#userProjects').addClass('show');
            $('#iteMyProyects').addClass('active');
        }
        if ($('#dataPage').hasClass('show')) {
            $('#dataPage').removeClass('show');
            $('#dataPage').addClass('hide');
            $('#iteMyDatas').removeClass('active');
        }
        if ($('#donateProyects').hasClass('show')) {
            $('#donateProyects').removeClass('show');
            $('#donateProyects').addClass('hide');
            $('#iteMyMovements').removeClass('active');
        }
        break;
    case 'myMovements':
        if ($('#userProjects').hasClass('show')) {
            $('#userProjects').removeClass('show');
            $('#userProjects').addClass('hide');
            $('#iteMyProyects').removeClass('active');
        }
        if ($('#dataPage').hasClass('show')) {
            $('#dataPage').removeClass('show');
            $('#dataPage').addClass('hide');
            $('#iteMyDatas').removeClass('active');
        }
        if ($('#donateProyects').hasClass('hide')) {
            $('#donateProyects').removeClass('hide');
            $('#donateProyects').addClass('show');
            $('#iteMyMovements').addClass('active');
        }
        break;
    case 'myDatas':
        if ($('#userProjects').hasClass('show')) {
            $('#userProjects').removeClass('show');
            $('#userProjects').addClass('hide');
            $('#iteMyProyects').removeClass('active');

        }
        if ($('#dataPage').hasClass('hide')) {
            $('#dataPage').removeClass('hide');
            $('#dataPage').addClass('show');
            $('#iteMyDatas').addClass('active');
        }
        if ($('#donateProyects').hasClass('show')) {
            $('#donateProyects').removeClass('show');
            $('#donateProyects').addClass('hide');
            $('#iteMyMovements').removeClass('active');
        }
        break;
    }

};