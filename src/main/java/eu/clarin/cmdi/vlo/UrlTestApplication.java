package eu.clarin.cmdi.vlo;

import eu.clarin.cmdi.vlo.historyapi.HistoryApiAjaxRequestTargetListener;
import java.util.Map;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see eu.clarin.cmdi.Start#main(String[])
 */
public class UrlTestApplication extends WebApplication implements ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(UrlTestApplication.class);

    private ApplicationContext applicationContext;

    /**
     * @return the home page of this application
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return UrlTestPage.class;
    }

    /**
     *
     * @return the active VLO wicket application
     */
    public static UrlTestApplication get() {
        return (UrlTestApplication) Application.get();
    }

    /**
     * Method needed for dynamic injection of application context (as happens in
     * unit tests)
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void init() {
        super.init();
        getAjaxRequestTargetListeners().add(new HistoryApiAjaxRequestTargetListener());
    }


}
