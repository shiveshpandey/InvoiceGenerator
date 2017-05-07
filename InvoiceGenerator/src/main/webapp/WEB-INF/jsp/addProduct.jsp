<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://www.w3schools.com/lib/w3-theme-indigo.css">

<script type="text/javascript">
	function validateData() {
		if (null == document.getElementById("product").value
				|| "" == document.getElementById("product").value) {
			document.getElementById("product").value = "";
			document.getElementById("product").focus();
			alert("Please provide a valid Product Name");
			return false;
		} else if (null == document.getElementById("description").value
				|| "" == document.getElementById("description").value) {
			document.getElementById("description").value = "";
			document.getElementById("description").focus();
			alert("Please provide a valid description");
			return false;
		} else if (isNaN(document.getElementById("quantity").value)
				|| null == document.getElementById("quantity").value
				|| "" == document.getElementById("quantity").value) {
			document.getElementById("quantity").value = "";
			document.getElementById("quantity").focus();
			alert("Please provide a valid quantity");
			return false;
		} else if (isNaN(document.getElementById("unitPrice").value)
				|| null == document.getElementById("unitPrice").value
				|| "" == document.getElementById("unitPrice").value) {
			document.getElementById("unitPrice").value = "";
			document.getElementById("unitPrice").focus();
			alert("Please provide a valid unit-price");
			return false;
		} else if (isNaN(document.getElementById("tax").value)
				|| null == document.getElementById("tax").value
				|| "" == document.getElementById("tax").value) {
			document.getElementById("tax").value = "";
			document.getElementById("tax").focus();
			alert("Please provide a valid tax percentage");
			return false;
		} else if (isNaN(document.getElementById("discount").value)
				|| null == document.getElementById("discount").value
				|| "" == document.getElementById("discount").value) {
			document.getElementById("discount").value = "";
			document.getElementById("discount").focus();
			alert("Please provide a valid discount");
			return false;
		} else {
			document.getElementById("quantity").value = checkPrecision(document
					.getElementById("quantity"));
			document.getElementById("unitPrice").value = checkPrecision(document
					.getElementById("unitPrice"));
			document.getElementById("discount").value = checkPrecision(document
					.getElementById("discount"));
			document.getElementById("tax").value = checkPrecision(document
					.getElementById("tax"));

			return true;
		}

	}
</script>
<body>
	<div class="w3-card-4" align="center">

		<div class="w3-container w3-theme w3-card-2">
			<h2>Add New Product</h2>
		</div>
		<form action="addNewProduct" method="post"
			enctype="application/x-www-form-urlencoded">
			<input type="hidden" name="companyId" id="companyId"
				value="${companyId}">
			<div style="background-color: #DEB887">
				<h4>${msgToUser}</h4>
			</div>
			<table id="borderManageTable" border="1" class="w3-table w3-striped"
				style="width: 800px">
				<tr>
					<td><table>
							<tr>
								<td>Product</td>
								<td><input type="text" name="product" style="width: 180px"
									id="product" value=""></td>
								<td>&nbsp;Default Quantity</td>
								<td><input type="text" style="width: 180px" name="quantity"
									id="quantity" value=""></td>
							</tr>
							<tr>
								<td rowspan="3">Description</td>
								<td rowspan="3"><textarea rows="6" cols="20"
										style="width: 180px" name="description" id="description"></textarea></td>
								<td>&nbsp;Unit Price (&#8377;)</td>
								<td><input type="text" style="width: 180px"
									name="unitPrice" id="unitPrice" value=""></td>
							</tr>
							<tr>
								<td>Default Tax (%)</td>
								<td><input type="text" style="width: 180px" name="tax"
									id="tax" value=""></td>
							</tr>
							<tr>
								<td>Default Discount (&#8377;)</td>
								<td><input type="text" style="width: 180px" name="discount"
									id="discount" value=""></td>
							</tr>
							<tr>
								<td align="center" colspan="4"><div align="center">
										<input type="submit" name="Add Product" id=" Add Product "
											onclick="return validateData();"
											value="&nbsp;&nbsp;&nbsp;&nbsp;Add Product&nbsp;&nbsp;&nbsp;&nbsp;" />
									</div></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</form>
		<table style="width: 50%">
			<tr>
				<td align="left"><h5><a href="home">Home page</a></h5></td>
				<td align="right"><h5><a href="loadProduct">Invoice Generation page</a></h5></td>
			</tr>
		</table>
	</div>
</body>
