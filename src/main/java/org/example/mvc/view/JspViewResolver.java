package org.example.mvc.view;

import static org.example.mvc.view.RedirectView.DEFAULT_REDIRECT_PREFIX;

public class JspViewResolver implements ViewResolver{
    /**
     * redirect 형태면 RedirectView 로 그렇지 않으면 JspView 로
     */
    @Override
    public View resolveViewName(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }
        return new JspView(viewName + ".jsp");
    }
}
