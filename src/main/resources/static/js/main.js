;(function(a) {

    a.fn.hoverDelay = function(c, f, g, b) {

        var g = g || 200,

        b = b || 200,

        f = f || c;

        var e = [],

        d = [];

        return this.each(function(h) {

            a(this).mouseenter(function() {

                var i = this;

                clearTimeout(d[h]);

                e[h] = setTimeout(function() {

                    c.apply(i)

                },

                g)

            }).mouseleave(function() {

                var i = this;

                clearTimeout(e[h]);

                d[h] = setTimeout(function() {

                    f.apply(i)

                },

                b)

            })

        })

    }

})(jQuery);

jQuery(function() {

jQuery(".sidebar").hoverDelay(function() {



        jQuery("#sidebar_box").show();

        jQuery(".sidebar_focus s").addClass("s_down");

    },

    function() {

        jQuery("#sidebar_box").hide();

        jQuery(".sidebar_item h1 s").removeClass("s_down");

    });



    // jQuery(".sidebar_item li").hoverDelay(function() {

    //         jQuery(".banner.f-left").css("z-index","0");

    //         jQuery(".f-lanmu.f-right").css({"position":"relative","z-index":"0"});

    //         jQuery(this).siblings().find("h1").removeClass("sidebar_focus");

    //         jQuery(this).siblings().find(".sidebar_popup").hide(0);



    //     jQuery(this).find("h1").addClass("sidebar_focus");

    //     jQuery(this).find(".sidebar_popup").show(0);

    // },

    // function() {

    //     jQuery(".banner.f-left").css("z-index","3333");

    //     jQuery(".f-lanmu.f-right").css({"position":"relative","z-index":"3333"});



    //     // jQuery(this).find("h1").removeClass("sidebar_focus");

    //     // jQuery(this).find(".sidebar_popup").hide(0);

    // });





   //   jQuery(".sidebar_item li").click(function() {

   //      console.log(jQuery(this).children('div.sidebar_popup').css('display'));

   //      if(jQuery(this).children('div.sidebar_popup').css('display')=='none'){

   //          jQuery(".banner.f-left").css("display","none");

   //          jQuery(".f-lanmu.f-right").css("display","none");

   //          jQuery(this).siblings().find("h1").removeClass("sidebar_focus");

   //          jQuery(this).siblings().find(".sidebar_popup").hide(0);

   //          jQuery('.zy-sidebar_item-bg').hide(0);



   //          jQuery(this).find("h1").addClass("sidebar_focus");

   //          jQuery(this).find(".sidebar_popup").show(0);



   //      }else{

            

   //      }

   //  },<?php echo $this->getUrl() ; ?>

   // );



 jQuery(document).click(function(){

       jQuery(".banner.f-left").css("display","none");

       jQuery('.zy-sidebar_item-bg').css('background','url(http://www.chinatruckparts.com/skin/frontend/rwd/kailai/kailai_images/logo2.5.png) no-repeat center center');

    });





    jQuery(".sidebar_item li h1").click(function() {

        if(jQuery(this).next().css('display')=='none'){

            jQuery(".banner.f-left").css("display","none");

            jQuery(".f-lanmu.f-right").css("display","none");

            // jQuery(this).siblings().find("h1").removeClass("sidebar_focus");

            jQuery(this).parent().siblings().find(".sidebar_popup").hide(0);

            // jQuery('.zy-sidebar_item-bg').hide(0);



            // jQuery(this).find("h1").addClass("sidebar_focus");

            jQuery(this).next().show(0);



        }else if(jQuery(this).next().css('display')=='block'){

            jQuery(".banner.f-left").css("display","block");

            jQuery(".f-lanmu.f-right").css("display","block");

            // jQuery('.zy-sidebar_item-bg').show(0);

            jQuery(this).next().hide(0);

            

        }

    },

   );



     jQuery(".banner.f-left .banner-cont .swiper-container").next().css("cursor","pointer");

    // jQuery(document).click(function(){

    //     jQuery(".bran-tc.f-left.sidebar .sidebar_item li").find("h1").removeClass("sidebar_focus");

    //     jQuery(".bran-tc.f-left.sidebar .sidebar_item li").find(".sidebar_popup").hide(0);

    // });



    jQuery(".banner.f-left .banner-cont .swiper-container").next().click(function(){

        jQuery(".banner.f-left").css("display","none");

        // jQuery(".f-lanmu.f-right").css("display","none");

    });



    jQuery(".zy-bg-img-list").scroll(function(){

         var jQuerythis =jQuery(this),  

         viewH =jQuery(this).height(),//可见高度  

         contentH =jQuery(this).get(0).scrollHeight,//内容高度  

         scrollTop =jQuery(this).scrollTop();//滚动高度  

        //if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容  

        if(scrollTop/(contentH -viewH)>=0.95){ //到达底部100px时,加载新内容  

            // console.log(scrollTop);

            jQuery(this).siblings(".zy-bg-img-list-img1").show();

            jQuery(this).siblings(".zy-bg-img-list-img2").hide();

        }  else if(scrollTop/(contentH -viewH)<=0.05){

             jQuery(this).siblings(".zy-bg-img-list-img1").hide();

            jQuery(this).siblings(".zy-bg-img-list-img2").show();

        }

    });

    



    // jQuery(".").each(function(){

    //     console.log("-----------------------");

    //     console.log(jQuery(this).children().children().length());

        // if(jQuery(this).children().children().length()>3){ 

        //     jQuery(this).siblings(".zy-bg-img-list-img2").show();

        // } 

    

    // });



    



     jQuery(".zy-bg-img-list").each(function(){

    if(jQuery(this).children().children().length>3){ 

            jQuery(this).siblings(".zy-bg-img-list-img2").show();

        } 

  });



});