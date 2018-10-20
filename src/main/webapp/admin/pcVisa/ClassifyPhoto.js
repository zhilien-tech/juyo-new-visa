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
        let item = this.getClassifyList(it.numType)
        let _url = item.length === 0 ? '' : item[0]['url']
        this.oncePhotoHandler(
            $(it.selector), 
            _url
        );
    }

    multiplePhoto(it) {
        this.multiplePhotoHandler($(it.selector), this.getClassifyList(it.numType));
    }

    getClassifyList(tp) {
        return this.credentials.filter(it => it.type === tp);
    };

    oncePhotoHandler($selector, path) {$selector.attr('src', path)};

    multiplePhotoHandler($selector, groups) {
        if (groups.length < 1) return 0;
        groups.forEach(it => void $selector.after($selector.clone().children('img').attr('src', it.url).end()));
        $selector.eq(0).remove();
    }
}