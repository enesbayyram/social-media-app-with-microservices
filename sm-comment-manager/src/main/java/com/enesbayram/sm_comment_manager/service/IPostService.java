package com.enesbayram.sm_comment_manager.service;

import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;

public interface IPostService extends IBaseCrudService<Post> , IBaseDbService<Post> {
}
