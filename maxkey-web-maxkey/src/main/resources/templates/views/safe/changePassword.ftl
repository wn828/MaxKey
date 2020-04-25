<!DOCTYPE HTML >
<html>
<head>
		<#include  "../layout/header.ftl"/>
		<#include  "../layout/common.cssjs.ftl"/>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div class="container">
<table width="100%">
  <tr>
    <td>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/safe/changePassword"> 

	  <table   class="table table-bordered" >
			<tbody>
			<tr>
				<th  colspan="2"><@locale code="login.password.changepassword" /></th>
			</tr>
			<tr>
				<th><@locale code="userinfo.displayName" /> :</th>
				<td>
					<input  class="form-control"  readonly type="text" id="displayName" name="displayName" class="required" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /> :</th>
				<td>
					<input  class="form-control"  readonly type="text" id="username" name="username" class="required" title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.oldPassword" /> :</th>
				<td>
					<input  class="form-control"  type="password" id="oldPassword" name="oldPassword" class="required" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.newPassword" />:</th>
				<td>
					<input  class="form-control"  type="password" id="newPassword" name="newPassword" class=" required" title="" value=""  required="" />

				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.confirmPassword" />:</th>
				<td nowrap>
					<input  class="form-control"  type="password" id="confirmPassword" name="confirmPassword" class="{ required: true, equalTo: '#newPassword' }" title="" value=""  required="" />
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button btn btn-primary" style="width:100px"  type="button"    id="submitBtn" value="<@locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</td>
  </tr>
</table>
</div>
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
<body>
</html>