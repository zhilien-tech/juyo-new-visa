'use strict';
class ClassifyPhoto {
    constructor(credentials) {
        this.credentials = credentials;
    }

    init(photoGroups) {
        let _this = this;
        photoGroups.forEach(it => {
            if (!it.type) return _this.oncePhoto(it);
            _this.multiplePhoto(it);
        });
    }

    oncePhoto(it) {
        this.oncePhotoHandler($(it.selector), this.getClassifyList(it.numType)[0]['url']);
    }

    multiplePhoto(it) {
        this.multiplePhotoHandler($(it.selector), this.getClassifyList(it.numType));
    }

    getClassifyList(tp) {return this.credentials.filter(it => it.type === tp)};

    oncePhotoHandler($selector, path) {$selector.attr('src', path)};

    multiplePhotoHandler($selector, groups) {
        if (groups.length < 1) return 0;
        groups.forEach(it => void $selector.after($selector.clone().children('img').attr('src', it.url).end()));
        $selector.eq(0).remove();
    }
}