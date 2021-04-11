package com.last.demo.util.tree;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: xiehh
 * @Date:2020/12/17 17:05
 * @ClassName:tree
 */
public class treeController {
    /**
     * 以下三个方法是根据国民经济行业id  对应显示选中状态
     * @param id
     * @return
     */
    public List<IndustryMapTreeVo> showStatusByindustryCode(List<String> id) {
        List<DicIndustry> list = new ArrayList<>();

        Map<String, List<IndustryMapTreeVo>> map = convert(list, id);
        List<IndustryMapTreeVo> rootlist = map.get("root");
        setSubList(rootlist, map);
        return rootlist;
    }

    /**
     * 将数据库查询数据转换成前端需要返回的vo 在里面进行节点状态的修改
     * @param list  数据库查询的数据
     * @param id   需要选中行业的id
     * @return
     */
    private Map<String, List<IndustryMapTreeVo>> convert(List<DicIndustry> list, List<String> id) {
        List<IndustryMapTreeVo> voList = new ArrayList<>();
        for (DicIndustry d : list) {
            IndustryMapTreeVo vo = new IndustryMapTreeVo();
            vo.setTitle(d.getIndustryName());
            vo.setKey(d.getIndustryCode());//当前对象的industry_code
            vo.setLevel(d.getLevel());//当前节点的级数
            vo.setpId(d.getParentCode());//当前节点的父类code
            vo.setStatus(id.contains(d.getIndustryCode()) ? "选中" : "未选中");
            vo.setExpanded(id.contains(d.getIndustryCode()) ? true : false);//是否展开
            vo.setIsLeaf("4".equals(d.getLevel())?true:false);//判断是否是叶节点 当level等于4时  赋值为true
            voList.add(vo);
        }
        //groupingBy 分组排序 这里使用DicIndustry的父类code进行排序  返回的是以DicIndustry的parentid为键的   DicIndustry本身为value的map
        Map<String, List<IndustryMapTreeVo>> listMap = voList.stream().collect(Collectors.groupingBy(IndustryMapTreeVo::getpId));
        return listMap;
    }

    /**
     * 通过递归的方式得出下面的菜单
     * @param parentList  上一级菜单的集合  初始化调用时 传入的是一级菜单的集合
     * @param treeMap  查询全部的map集合  k - 父类id  v - 当前对象
     */
    private void setSubList(List<IndustryMapTreeVo> parentList, Map<String, List<IndustryMapTreeVo>> treeMap) {
        for (IndustryMapTreeVo industry : parentList) {
            //根据DicIndustry中industry_code取出list集合  industry_code也就是当前对象的父类id 取出是list集合
            List<IndustryMapTreeVo> sublist = treeMap.get(industry.getKey());
            if (CollectionUtils.isEmpty(sublist)) {
                continue;
            }
            //将取出的list集合赋值给一级菜单的子菜单
            industry.setChildren(sublist);
            //递归调用这个方法 查询剩余的子菜单
            setSubList(sublist, treeMap);
        }
    }


}
