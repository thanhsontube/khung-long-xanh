
package com.khunglong.xanh.json;

import java.util.ArrayList;
import java.util.List;

public class FbComments{
	
	
   	public FbComments(List<CmtData> data, CmtPaging paging) {
	    super();
	    this.data = data;
	    this.paging = paging;
    }
	public FbComments() {
	    super();
	    data = new ArrayList<CmtData>();
    }
	public List<CmtData> data;
   	public CmtPaging paging;

 	
}
