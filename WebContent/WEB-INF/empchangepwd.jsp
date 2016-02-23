
<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
	<h3 class="panel-title">Change Your Password</h3>
</div>
<div class="panel-body">
<jsp:include page="template-result.jsp" /><br>
<p>Input length should be no more than 15 </p>
	<div class="col-sm-8" style="margin-left: ">
		<form role="form" class="form-horizontal">
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-6 control-label">New
					password</label>
				<div class="col-sm-6">
					<input type="password" pattern = "(.){1,15}" class="form-control" name="newPassword">
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1" class="col-sm-6 control-label">Confirm
					New Password</label>
				<div class="col-sm-6">
					<input type="password" pattern = "(.){1,15}" class="form-control"
						name="confirmNewPassword">
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1" class="col-sm-6 control-label">
				</label>
				<div class="col-sm-6">
					<button type="submit" class="btn btn-primary">Save Changes</button>
				</div>
			</div>
		</form>
	</div>
</div>


