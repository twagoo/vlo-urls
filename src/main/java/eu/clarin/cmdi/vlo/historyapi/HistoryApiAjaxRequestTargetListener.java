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
package eu.clarin.cmdi.vlo.historyapi;

import java.util.Map;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.encoding.UrlEncoder;

/**
 *
 * @author Twan Goosen <twan@clarin.eu>
 */
public class HistoryApiAjaxRequestTargetListener extends AjaxRequestTarget.AbstractListener {

    @Override
    public void onBeforeRespond(Map<String, Component> map, AjaxRequestTarget target) {
        super.onBeforeRespond(map, target);
        final Page page = target.getPage();
        if (page instanceof HistoryApiAware) {
            target.appendJavaScript(createParamsScript((HistoryApiAware) page));
        }
    }

    private String createParamsScript(HistoryApiAware page) {
        final StringBuilder state = new StringBuilder();
        final Map<String, IModel> map = page.getUrlParametersMap();
        map.keySet().forEach((key) -> {
            final String value = map.get(key).getObject().toString();
            final String encodedValue = UrlEncoder.QUERY_INSTANCE.encode(value, "UTF-8");
            state.append(key).append("=").append(encodedValue);
        });

        return ""
                + "var path = this.window.location.pathname;"
                + "var queryParams = this.window.location.search;"
                + "var newParams = '?';"
                + "var newUrl = path + newParams + '" + state.toString() + "';"
                + "console.log('New url: ' + newUrl);"
                + "var stateObj = { foo: 'bar' };"
                + "history.pushState(stateObj, 'page', newUrl);";
    }

}
