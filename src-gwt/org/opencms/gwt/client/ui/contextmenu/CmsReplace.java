/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.gwt.client.ui.contextmenu;

import org.opencms.gwt.client.ui.css.I_CmsImageBundle;
import org.opencms.gwt.client.ui.css.I_CmsLayoutBundle;
import org.opencms.gwt.client.ui.input.upload.CmsUploadButton;
import org.opencms.gwt.client.ui.replace.CmsReplaceHandler;
import org.opencms.gwt.shared.CmsContextMenuEntryBean;
import org.opencms.util.CmsUUID;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * The replace resource context menu entry.<p>
 */
public class CmsReplace extends A_CmsContextMenuItem implements I_CmsHasContextMenuCommand {

    /** The replace handler. */
    private CmsReplaceHandler m_replaceHandler;

    /**
     * Constructor.<p>
     * 
     * @param structureId the structure id of the resource to replace
     * @param handler the context menu handler
     * @param bean the menu entry bean
     */
    protected CmsReplace(final CmsUUID structureId, final I_CmsContextMenuHandler handler, CmsContextMenuEntryBean bean) {

        super(bean.getLabel());
        m_replaceHandler = new CmsReplaceHandler(structureId);
        m_replaceHandler.setCloseHandler(new CloseHandler<PopupPanel>() {

            public void onClose(CloseEvent<PopupPanel> arg0) {

                handler.refreshResource(structureId);
            }
        });
        CmsUploadButton button = new CmsUploadButton(m_replaceHandler);
        button.setText(getText());
        button.setImageClass(I_CmsImageBundle.INSTANCE.contextMenuIcons().replace());
        initWidget(button);
        setStyleName(I_CmsLayoutBundle.INSTANCE.contextmenuCss().cmsMenuItem());
        addStyleName(I_CmsLayoutBundle.INSTANCE.uploadButton().uploadButton());
    }

    /**
     * Returns the context menu command according to 
     * {@link org.opencms.gwt.client.ui.contextmenu.I_CmsHasContextMenuCommand}.<p>
     * 
     * @return the context menu command
     */
    public static I_CmsContextMenuCommand getContextMenuCommand() {

        return new I_CmsContextMenuCommand() {

            public void execute(CmsUUID structureId, I_CmsContextMenuHandler handler, CmsContextMenuEntryBean bean) {

                // nothing to do, will be handled by the widget
            }

            public String getCommandIconClass() {

                return null;
            }

            public A_CmsContextMenuItem getItemWidget(
                CmsUUID structureId,
                I_CmsContextMenuHandler handler,
                CmsContextMenuEntryBean bean) {

                return new CmsReplace(structureId, handler, bean);
            }

            public boolean hasItemWidget() {

                return true;
            }
        };
    }

    /**
     * @see org.opencms.gwt.client.ui.contextmenu.A_CmsContextMenuItem#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    @Override
    public void onClick(ClickEvent event) {

        m_replaceHandler.setMenuItem(this);
    }

}
