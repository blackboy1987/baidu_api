package com.bootx.dao.impl;

import com.bootx.dao.FileListDao;
import com.bootx.entity.FileList;
import jakarta.persistence.TypedQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FileListDaoImpl extends BaseDaoImpl<FileList,Long> implements FileListDao {
    @Override
    public List<FileList> findChildren(FileList fileList, boolean recursive, Integer count) {
        TypedQuery<FileList> query;
        if (recursive) {
            if (fileList != null) {
                String jpql = "select fileList from FileList fileList where fileList.treePath like :treePath order by fileList.grade asc, fileList.order asc";
                query = entityManager.createQuery(jpql, FileList.class).setParameter("treePath", "%" + FileList.TREE_PATH_SEPARATOR + fileList.getId() + FileList.TREE_PATH_SEPARATOR + "%");
            } else {
                String jpql = "select fileList from FileList fileList order by fileList.grade asc, fileList.order asc";
                query = entityManager.createQuery(jpql, FileList.class);
            }
            if (count != null) {
                query.setMaxResults(count);
            }
            List<FileList> result = query.getResultList();
            sort(result);
            return result;
        } else {
            String jpql = "select fileList from FileList fileList where fileList.parent = :parent order by fileList.order asc";
            query = entityManager.createQuery(jpql, FileList.class).setParameter("parent", fileList);
            if (count != null) {
                query.setMaxResults(count);
            }
            return query.getResultList();
        }
    }

    /**
     * 排序商品分类
     *
     * @param productCategories
     *            商品分类
     */
    private void sort(List<FileList> productCategories) {
        if (CollectionUtils.isEmpty(productCategories)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<>();
        for (FileList fileList : productCategories) {
            orderMap.put(fileList.getId(), fileList.getOrder());
        }
        Collections.sort(productCategories, new Comparator<FileList>() {
            @Override
            public int compare(FileList fileList1, FileList fileList2) {
                Long[] ids1 = (Long[]) ArrayUtils.add(fileList1.getParentIds(), fileList1.getId());
                Long[] ids2 = (Long[]) ArrayUtils.add(fileList2.getParentIds(), fileList2.getId());
                Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
                Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
                CompareToBuilder compareToBuilder = new CompareToBuilder();
                while (iterator1.hasNext() && iterator2.hasNext()) {
                    Long id1 = iterator1.next();
                    Long id2 = iterator2.next();
                    Integer order1 = orderMap.get(id1);
                    Integer order2 = orderMap.get(id2);
                    compareToBuilder.append(order1, order2).append(id1, id2);
                    if (!iterator1.hasNext() || !iterator2.hasNext()) {
                        compareToBuilder.append(fileList1.getGrade(), fileList2.getGrade());
                    }
                }
                return compareToBuilder.toComparison();
            }
        });
    }
}
