package com.bootx.controller.api;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Area;
import com.bootx.entity.BaseEntity;
import com.bootx.repository.AreaRepository;
import com.bootx.service.AreaService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("apiAdminAppController")
@RequestMapping("/admin/api/app")
public class AreaController {

    @Resource
    private AreaService areaService;

    @Resource
    private AreaRepository areaRepository;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable,String name){
        Page<Area> page = areaService.findPage(pageable,name);
        return Result.success(page);
    }

    @PostMapping("/list1")
    @JsonView(BaseEntity.PageView.class)
    public Result list1(Pageable pageable,String name){
        org.springframework.data.domain.Pageable pageable1 = org.springframework.data.domain.Pageable.ofSize(pageable.getPageSize());
        pageable1.withPage(pageable.getPageNumber());
        org.springframework.data.domain.Page<Area> all = areaRepository.findAll(new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate restrictions = criteriaBuilder.conjunction();
                if(StringUtils.isNotBlank(name)){
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
                }
                return restrictions;
            }
        }, pageable1);
        return Result.success(new Page<Area>(all.getContent(),all.getTotalElements(),pageable));
    }
}
