package com.ynetech.apiserver;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class TestModel {

    @NonNull
    private String id;

}
