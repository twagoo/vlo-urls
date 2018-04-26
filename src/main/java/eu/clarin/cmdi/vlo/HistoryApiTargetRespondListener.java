/*
 * Copyright (C) 2018 CLARIN
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.clarin.cmdi.vlo;

import java.util.HashMap;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.encoding.UrlEncoder;

/**
 *
 * @author Twan Goosen <twan@clarin.eu>
 */
public class HistoryApiTargetRespondListener implements AjaxRequestTarget.ITargetRespondListener {

    private final IModel<HashMap<String, IModel<?>>> pageParamsModel;

    public HistoryApiTargetRespondListener() {
        pageParamsModel = new Model<>(new HashMap<>());
    }

    public HistoryApiTargetRespondListener addModel(String key, IModel<?> model) {
        pageParamsModel.getObject().put(key, model);
        return this;
    }

    @Override
    public void onTargetRespond(AjaxRequestTarget target) {

        StringBuilder state = new StringBuilder();
        final HashMap<String, IModel<?>> map = pageParamsModel.getObject();
        map.keySet().forEach((key) -> {
            final String value = map.get(key).getObject().toString();
            final String encodedValue = UrlEncoder.QUERY_INSTANCE.encode(value, "UTF-8");
            state.append(key).append("=").append(encodedValue);
        });
        
        target.appendJavaScript(""
                + "var path = this.window.location.pathname;"
                + "var queryParams = this.window.location.search;"
                + "var newParams = '?';"
//                + "if (queryParams && queryParams !== '') {"
//                + " sessionIndex = queryParams.replace(/\\?(\\d+).*/,'$1');"
//                + " if(sessionIndex != '') {"
//                + "   newParams += sessionIndex + '&'; "
//                + " }"
//                + " console.log('New params: ' + newParams);"
//                + "}"
                + "var newUrl = path + newParams + '" + state.toString() + "';"
                + "console.log('New url: ' + newUrl);"
                + "var stateObj = { foo: 'bar' };"
                + "history.pushState(stateObj, 'page', newUrl);");
    }

    @Override
    public String toString() {
        return pageParamsModel.getObject().toString();
    }

}
