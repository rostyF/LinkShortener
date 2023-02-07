package com.example.proglink;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    @Transactional
    public Long saveUrl(UrlDTO urlDTO){
        var urlRecord = urlRepository.findByUrl(urlDTO.getUrl());

        if(urlRecord == null){
            urlRecord = new UrlRecord(urlDTO.getUrl());
            urlRepository.save(urlRecord);
        }
        return urlRecord.getId();
    }

    @Transactional
    public String getUrl(long id){
        var urlOpt = urlRepository.findById(id);
        if (urlOpt.isEmpty()){
            return null;
        }

        var urlRecord = urlOpt.get();
        urlRecord.setCount(urlRecord.getCount() + 1);
        urlRecord.setLastAccess(new Date());

        return urlRecord.getUrl();
    }
}
