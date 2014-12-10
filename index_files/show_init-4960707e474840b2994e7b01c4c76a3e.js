(function() {
var e;
e = $.noConflict(!0), window.$ || (window.$ = window.jQuery = e), $(function() {
var e, t;
return t = new Bobcat.SocialMediaConfig({
url:$S.page_meta.social_media_config.url,
fb_app_id:$S.page_meta.social_media_config.fb_app_id,
title:$S.page_meta.social_media_config.title,
image:$S.page_meta.social_media_config.image,
description:$S.page_meta.social_media_config.description
}), window.social_media_config = t, window.slide_navigator = new Bobcat.Navigator(), 
$B.isStatic() ? window.edit_page = new Bobcat.ShowPage({}) :(window.edit_page = new Bobcat.ShowPage(data), 
window.pageTransformers = new Bobcat.PageTransformer($("body"), !1), window.pageTransformers.transform()), 
e = {
page_id:$S.page_meta.id,
user_id:$S.page_meta.user.id,
permalink:$S.page_meta.permalink,
membership:$S.page_meta.user.membership,
created_at:$S.page_meta.created_at,
showStrikinglyLogo:$S.page_meta.show_strikingly_logo,
theme:$S.page_meta.theme
}, Bobcat.PageAE = new Bobcat.PageAnalyticsEngine(e), $B.isStatic() ? (console.log("end user static view"), 
window.edit_page.initAfterBindings()) :$B.isHeadlessRendering() ? (console.log("headless rendering"), 
window.edit_page.initBindings()) :(console.log("end user normal view"), window.edit_page.initBindings(), 
window.edit_page.initAfterBindings()), window.slide_navigator.init(), $S.conf.preview_mode || ($B.isHeadlessRendering() || $S.conf.is_screenshot_rendering || ($B.PageAE.logPageView(), 
$B.PageAE.trackPageEvent()), $B.isStatic()) ? void 0 :Bobcat.PageAE.setInternalTracking();
});
}).call(this);