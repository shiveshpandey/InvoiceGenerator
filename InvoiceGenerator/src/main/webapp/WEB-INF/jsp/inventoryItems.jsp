<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://www.w3schools.com/lib/w3-theme-indigo.css">

<script type="text/javascript">
	function onProductChangeValueReset() {
		var count = 0;
		var index = document.getElementById("product").selectedIndex;
		<c:forEach items="${productList}" var="item">
		if (index == count++) {
			document.getElementById("product").value = "${item.productId}";
			document.getElementById("description").value = "${item.description}";
			document.getElementById("quantity").value = "${item.quantity}";
			document.getElementById("unitPrice").value = "${item.unitPrice}";
			document.getElementById("tax").value = 0;
			document.getElementById("discount").value = 0;
			document.getElementById("total").value = document
					.getElementById("quantity").value
					* document.getElementById("unitPrice").value;
		}
		</c:forEach>
	}
	function onloadProductListPopulate() {
		var i = 0;
		var product = document.getElementById("product");
		<c:forEach items="${productList}" var="item">
		var option = document.createElement('option');
		option.text = "${item.product}";
		option.value = "${item.productId}";
		product.add(option, i++);
		</c:forEach>
	}

	function isInteger(x) {
		return x % 1 === 0;
	}

	function validateAddRowData() {
		if (null == document.getElementById("product").value
				|| "" == document.getElementById("product").value) {
			document.getElementById("product").value = "";
			document.getElementById("product").focus();
			alert("Please select a valid Product Name");
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
		} else if (isNaN(document.getElementById("total").value)
				|| null == document.getElementById("total").value
				|| "" == document.getElementById("total").value) {
			document.getElementById("total").value = "";
			document.getElementById("total").focus();
			alert("Please provide a valid total value");
			return false;
		} else {
			document.getElementById("quantity").value = checkPrecision(document
					.getElementById("quantity"));
			document.getElementById("unitPrice").value = checkPrecision(document
					.getElementById("unitPrice"));
			document.getElementById("total").value = checkPrecision(document
					.getElementById("total"));
			autoCalculateFieldValues();
			return true;
		}

	}
	function autoCalculateFieldValues() {
		if (isNaN(document.getElementById("quantity").value)
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
		}

		document.getElementById("quantity").value = checkPrecision(document
				.getElementById("quantity"));
		document.getElementById("unitPrice").value = checkPrecision(document
				.getElementById("unitPrice"));

		var quantity = document.getElementById("quantity").value;
		var unitPrice = document.getElementById("unitPrice").value;
		var total = (quantity * unitPrice);

		document.getElementById("total").value = total;
		document.getElementById("total").value = checkPrecision(document
				.getElementById("total"));

	}
	function checkPrecision(obj) {
		if ((obj.value).lastIndexOf(".") == (obj.value).length - 1)
			return obj.value + "0";
		if ((obj.value).indexOf(".") == 0)
			return "0" + obj.value;
		return obj.value;
	}
	function deleteRow(r) {
		document.getElementById("addRowTable").rows[r].cells[6].innerHTML = "deleted";
		finalTotalAutoCalculate();
	}
	function addRecord() {
		autoCalculateFieldValues();
		if (validateAddRowData()) {
			if (document.getElementById("noProductMsg"))
				document.getElementById("borderManageTable").deleteRow(6);
			var table = document.getElementById("addRowTable");
			var row = table.insertRow(table.rows.length);

			var cell0 = row.insertCell(0);
			var cell1 = row.insertCell(1);
			var cell2 = row.insertCell(2);
			var cell3 = row.insertCell(3);
			var cell4 = row.insertCell(4);
			var cell5 = row.insertCell(5);
			var cell6 = row.insertCell(6);

			cell0.innerHTML = table.rows.length - 1;
			cell1.innerHTML = document.getElementById("product").options[document
					.getElementById("product").selectedIndex].innerHTML;
			cell2.innerHTML = document.getElementById("description").value;
			cell3.innerHTML = document.getElementById("quantity").value;
			cell4.innerHTML = document.getElementById("unitPrice").value;
			cell5.innerHTML = document.getElementById("total").value;
			cell6.innerHTML = "<button type='button' onclick='deleteRow(" + '"'
					+ (table.rows.length - 1) + '"' + ")' >Delete</button>";
			cell1.id = document.getElementById("product").value;
			finalTotalAutoCalculate();
		}
	}
	function finalTotalAutoCalculate() {
		if (isNaN(document.getElementById("tax").value)
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
			alert("Please provide a valid discount percentage");
			return false;
		}
		document.getElementById("discount").value = checkPrecision(document
				.getElementById("discount"));
		document.getElementById("tax").value = checkPrecision(document
				.getElementById("tax"));

		var a = 0;
		var table = document.getElementById("addRowTable");

		for (var r = 1; r < table.rows.length; r++) {
			if (table.rows[r].cells[6].innerHTML != "deleted") {
				a += table.rows[r].cells[5].innerHTML * 1;
			}
		}
		document.getElementById("subAmtDiv").innerText = a;
		document.getElementById("finalAmtDiv").innerText = "Final Total = { Sub Total } + { "
				+ document.getElementById("tax").value
				+ "% Tax i.e. "
				+ a
				* document.getElementById("tax").value
				/ 100
				+ " } - { Discount i.e. "
				+ document.getElementById("discount").value + " }  =  ";
		document.getElementById("finalAmt").innerText = (a * 1
				+ (a * document.getElementById("tax").value / 100) - document
				.getElementById("discount").value * 1);

	}
	function GeneratePdf() {
		var str = "";
		var table = document.getElementById("addRowTable");
		for (var r = 1; r < table.rows.length; r++) {
			if (table.rows[r].cells[6].innerHTML != "deleted") {
				str = str + table.rows[r].cells[0].innerHTML + "##"
						+ table.rows[r].cells[1].innerHTML + "##"
						+ table.rows[r].cells[2].innerHTML + "##"
						+ table.rows[r].cells[3].innerHTML + "##"
						+ table.rows[r].cells[4].innerHTML + "##"
						+ table.rows[r].cells[5].innerHTML + "##"
						+ table.rows[r].cells[1].id + "@@@";
			}
		}

		if (str == "" || str == null) {
			alert("Please add atleast one product");
			return false;
		} else {
			document.getElementById("pdfTextContent").value = str;
			return true;
		}
	}
	function resetForm() {
		location.reload();
	}
	function resetDataTable() {
		document.getElementById("product").selectedIndex = 0;
		document.getElementById("description").value = '';
		document.getElementById("quantity").value = 0;
		document.getElementById("unitPrice").value = 0;
		document.getElementById("tax").value = 0;
		document.getElementById("discount").value = 0;
		document.getElementById("total").value = 0;
	}
</script>
<body>
	<div class="w3-card-4" align="center">

		<div class="w3-container w3-theme w3-card-2">
			<h2>Invoice/Bill Form</h2>
		</div>
		<form action="generateInvoice" method="post"
			enctype="application/x-www-form-urlencoded">
			<input type="hidden" name="pdfTextContent" id="pdfTextContent"
				value="" /> <input type="hidden" name="companyId" id="companyId"
				value="${companyId}">
			<table id="borderManageTable" border="1" class="w3-table w3-striped"
				style="width: 55%">
				<thead>
					<tr style="background-color: #DEB887">
						<th>Seller Details</th>
						<th>Customer Details</th>
					</tr>
				</thead>
				<tbody>
					<tr></tr>
					<tr>
						<td>
							<table>
								<tr>
									<td>Name</td>
									<td><input type="text" name="companyName" id="companyName"
										value="${companyName}"></td>
								</tr>
								<tr>
									<td>Address</td>
									<td><textarea rows="4" cols="20" name="companyAddress"
											id="companyAddress">${companyAddress}</textarea></td>
								</tr>
								<tr>
									<td>Mobile/Tel</td>
									<td><input type="text" name="companyMobile"
										id="companyMobile" value="${companyMobile}"></td>
								</tr>
								<tr>
									<td>VAT/TIN #</td>
									<td><input type="text" name="companyVattin"
										id="companyVattin" value="${companyVattin}"></td>
								</tr>
								<tr>
									<td>CST #</td>
									<td><input type="text" name="companyCst" id="companyCst"
										value="${companyCst}"></td>
								</tr>
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td>Name</td>
									<td><input type="text" name="customerName"
										id="customerName" value=""></td>
								</tr>
								<tr>
									<td>Address</td>
									<td><textarea rows="6" cols="20" name="customerAddress"
											id="customerAddress"></textarea></td>
								</tr>
								<tr>
									<td>Mobile/Tel</td>
									<td><input type="text" name="customerMobile"
										id="customerMobile" value=""></td>
								</tr>
								<tr>
									<td>Email</td>
									<td><input type="text" name="customerEmail"
										id="customerEmail" value=""></td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;Order
							ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"
							name="orderId" id="orderId" value="${orderId}">
						</td>
						<td>&nbsp;&nbsp;Order Date&nbsp;&nbsp;&nbsp;<input
							type="text" name="orderDate" id="orderDate" value="${orderDate}"></td>
					</tr>
					<tr>
						<td colspan="2"><table>
								<tr>
									<td>Product</td>
									<td><select name="product" id="product"
										style="width: 100%" onchange="onProductChangeValueReset();">
									</select></td>
									<td>&nbsp;Quantity</td>
									<td><input type="text" name="quantity" id="quantity"
										onChange="autoCalculateFieldValues();" value=""></td>
								</tr>
								<tr>
									<td rowspan="2">Description</td>
									<td rowspan="2"><textarea rows="4" cols="20"
											name="description" id="description"></textarea></td>
									<td>&nbsp;Unit Price (&#8377;)</td>
									<td><input type="text" name="unitPrice" id="unitPrice"
										onChange="autoCalculateFieldValues();" value=""></td>
								</tr>

								<tr>
									<td>Total (&#8377;)</td>
									<td><input type="text" name="total" id="total" value=""></td>
								</tr>
								<tr>
									<td align="right" colspan="4"><div align="center">
											<input type="button" name="addRow" id="addRow" value="Add"
												onClick="addRecord();" />&nbsp;&nbsp;&nbsp;&nbsp; <input
												type="button" name="reset" id="reset" value="Reset"
												onClick="resetDataTable();" />
										</div></td>
								</tr>
								<tr>
									<td>Tax (%)</td>
									<td><input type="text" name="tax" id="tax"
										onChange="finalTotalAutoCalculate();" value=""></td>
									<td>Discount (&#8377;)</td>
									<td><input type="text" name="discount" id="discount"
										onChange="finalTotalAutoCalculate();" value=""></td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td colspan="2"><table id="addRowTable" border="1"
								class="w3-table w3-striped">
								<thead>
									<tr style="background-color: #DEB887">
										<th>S No</th>
										<th>Product</th>
										<th>Desc</th>
										<th>Qty</th>
										<th>Price</th>
										<th>Total</th>
										<th>*</th>
									</tr>
								</thead>
							</table></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><div id="noProductMsg"
								align="center">no products added</div></td>
					</tr>
					<tr>
						<td colspan="2">
							<table class="w3-striped" style="width: 100%">
								<tr>
									<td><div align="right">Sub Total =</div></td>
									<td><div align="right" id="subAmtDiv"
											style="font-weight: bold">0.0</div></td>
								</tr>
								<tr>
									<td><div align="right" id="finalAmtDiv">Final Total
											=</div></td>
									<td><div align="right" id="finalAmt"
											style="font-weight: bold">0.0</div></td>
								</tr>
								<tr>
									<td colspan="2"><div align="center">
											<input type="submit" name="generateInvoice"
												id="generateInvoice" value="Generate Invoice"
												onClick="return GeneratePdf();" />&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" name="cancel" id="cancel"
												value="     Reset Form     " onClick="resetForm();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div></td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<table style="width: 55%">
			<tr>
				<td align="left"><h5>
						<a href="home">Home page</a>
					</h5></td>
				<td align="right"><h5>
						<a href="addProduct">Add Product page</a>
					</h5></td>
			</tr>
		</table>
	</div>
	<script>
		onloadProductListPopulate();
		onProductChangeValueReset();
	</script>
</body>
