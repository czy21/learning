package com.learning.infranstructure.util;


import com.learning.infranstructure.model.SimpleItemModel;
import com.learning.infranstructure.model.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TreeUtil {

    public enum SortKind {
        ASC,
        DESC
    }

    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all, Predicate<T> rootPredicate) {
        return getTree(all, rootPredicate, null);
    }

    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all,
                                                             Predicate<T> rootPredicate,
                                                             Consumer<T> decoNodeFunc) {
        return getTree(all, rootPredicate, decoNodeFunc, TreeNode::getSort, SortKind.ASC);
    }

    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all,
                                                             Predicate<T> rootPredicate,
                                                             Consumer<T> decoNodeFunc,
                                                             Function<TreeNode<V>, Integer> sortFunc,
                                                             SortKind sortKind) {
        if (decoNodeFunc != null) {
            all.forEach(decoNodeFunc);
        }
        List<T> tree = all.stream().filter(rootPredicate).map(t -> buildChildren(all, t)).collect(Collectors.toList());
        if (sortFunc != null) {
            tree.forEach(t -> sortBy(t, sortFunc, sortKind));
            tree = tree.stream()
                    .sorted(sortKind == SortKind.ASC ? Comparator.comparing(sortFunc) : Comparator.comparing(sortFunc).reversed())
                    .collect(Collectors.toList());
        }
        return tree;
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> T buildChildren(List<T> items, T node) {
        List<TreeNode<V>> children = new ArrayList<>();
        items.forEach(t -> {
            if (node.getId().equals(t.getParentId())) {
                children.add(buildChildren(items, t));
            }
        });
        if (children.size() > 0) {
            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                node.getChildren().addAll((List) children);
            } else {
                node.setChildren(children);
            }
        }
        return node;
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> void sortBy(T item, Function<TreeNode<V>, Integer> sortFunc, SortKind sortKind) {
        if (CollectionUtils.isNotEmpty(item.getChildren())) {
            item.setChildren(item.getChildren().stream().sorted(sortKind == SortKind.ASC ? Comparator.comparing(sortFunc) : Comparator.comparing(sortFunc).reversed()).collect(Collectors.toList()));
            item.getChildren().forEach(t -> sortBy((T) t, sortFunc, sortKind));
        }
    }

    public static void main(String[] args) {
        List<SimpleItemModel<String>> list = new ArrayList<>();
        SimpleItemModel<String> t1 = SimpleItemModel.of("a", "a");
        t1.setParentValue(null);
        t1.setSort(1);
        SimpleItemModel<String> t2 = SimpleItemModel.of("b", "b");
        t2.setParentValue("a");
        t2.setSort(3);
        SimpleItemModel<String> t3 = SimpleItemModel.of("c", "c");
        t3.setParentValue("a");
        t3.setSort(2);
        list.add(t1);
        list.add(t2);
        list.add(t3);
        List<SimpleItemModel<String>> tree = TreeUtil.getTree(list, t -> t.getParentValue() == null);
        System.out.println("aaa");
    }

}
