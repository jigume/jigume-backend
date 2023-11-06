package com.jigume.domain.goods.entity;

import lombok.Getter;

@Getter
public enum DefaultImgUrl {

    DEFAULT_GOODS_IMG_URL(""), DEFAULT_MEMBER_PROFILE_IMG_URL("");

    private final String defaultImgUrl;

    DefaultImgUrl(String defaultImgUrl) {
        this.defaultImgUrl = defaultImgUrl;
    }
}
