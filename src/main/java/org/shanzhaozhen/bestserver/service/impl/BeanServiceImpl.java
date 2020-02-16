package org.shanzhaozhen.bestserver.service.impl;

import org.shanzhaozhen.bestserver.dto.BeanInfo;
import org.shanzhaozhen.bestserver.dto.MethodInfo;
import org.shanzhaozhen.bestserver.service.BeanService;
import org.shanzhaozhen.bestserver.utils.SpringContextUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BeanServiceImpl implements BeanService {

    @Override
    public List<BeanInfo> getBeanInfoList() {
        Map<String, Object> serviceObject = SpringContextUtils.getBeansWithAnnotation(Service.class);
        Assert.isTrue(serviceObject.size() > 0, "Spring容器中没有有效的Bean");
        return serviceObject.entrySet().stream().map(e -> new BeanInfo(e.getKey(), e.getValue().getClass().getName(), null)).collect(Collectors.toList());
    }

    @Override
    public BeanInfo getBeanInfoByName(String beanName) {
        Object bean = SpringContextUtils.getBean(beanName);
        Assert.notNull(bean, "该Bean不存在");

        Method[] methods = bean.getClass().getMethods();

        BeanInfo beanInfo = new BeanInfo(beanName, beanName.getClass().getName(), null);

        if (methods.length > 0) {
            List<MethodInfo> methodInfoList = Arrays.stream(methods).map(
                    methodInfo -> new MethodInfo(methodInfo.getName(), null, null,
//                            Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList()),
                            methodInfo.getParameterTypes())
            ).collect(Collectors.toList());

            beanInfo.setMethods(methodInfoList);
        }

        return beanInfo;
    }
}
