package sunjx.business.controller;

import sunjx.framework.annotation.Action;
import sunjx.framework.annotation.Controller;
import sunjx.framework.bean.View;

/**
 * Created by sunjx on 2018/1/10.
 */
@Controller
public class IndexController {

    @Action("GET:/")
    public View index(){
        return new View("index.jsp");
    }

}
