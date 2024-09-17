package com.github.basespring.application.app;

import com.github.basespring.application.base.*;
import com.github.basespring.application.commons.BeanCopy;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AppController extends BaseResController {


    private <DestDt, SrcDt, Src extends Page<SrcDt>> ResponseConverter<Src, PageResponse<DestDt>> getPageConverter(Class<DestDt> dtClass) {
        return new ResponseConverter<>() {
            @Override
            protected PageResponse<DestDt> convert(Src src) {
                List<DestDt> destDts = BeanCopy.copyCollection(src.getContent(), dtClass);
                return new PageResponse<>(src, destDts);
            }
        };
    }

    private <Dest, Src> ResponseConverter<Src, Dest> getDetailConverter(Class<Dest> destClass) {
        return new ResponseConverter<>() {
            @Override
            protected Dest convert(Src src) {
                return BeanCopy.copy(src, destClass);
            }

        };
    }

    private <T, E> ResponseEntity<Response<E>> responseConvertDetail(ServiceResolver<T> resolver, Class<E> tClass)  {
        return response(resolver, getDetailConverter(tClass));
    }

    protected <T, E> ResponseEntity<Response<PageResponse<E>>> responseConvertPage(ServiceResolver<Page<T>> resolver, Class<E> tClass) {
        return responsePage(resolver, getPageConverter(tClass));
    }


}