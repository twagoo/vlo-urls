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
import java.util.Random;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Twan Goosen <twan@clarin.eu>
 */
public class UrlTestPage extends WebPage {

    private final static Logger LOG = LoggerFactory.getLogger(UrlTestPage.class);

    private IModel<String> textModel;

    public UrlTestPage(PageParameters parameters) {
        super(parameters);
        textModel = new Model<>("text");

        final UrlFragmentStateSetter urlSetter
                = new UrlFragmentStateSetter()
                        .addModel("text", textModel);

        final Form form = new Form("form");
        final Label label = new Label("text", textModel);
        final AjaxFallbackButton button = new AjaxFallbackButton("button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                textModel.setObject("new text " + new Random().nextInt());
                if (target != null) {
                    target.add(form);
                    target.registerRespondListener(urlSetter);
                }
            }
        };

        Label paramsLabel = new Label("params", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return urlSetter.toString();
            }
        });

        add(form
                .add(label)
                .add(button)
                .add(paramsLabel)
                .setOutputMarkupId(true)
        );

    }

}
