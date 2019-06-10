/** EasyWeb iframe v3.1.2 date:2019-06-05 License By http://easyweb.vip */

layui.define(["jquery", "layer"], function(f) {
	var h = layui.jquery;
	var k = layui.layer;
	var a = ".layui-layout-admin>.layui-body";
	var l = a + ">.layui-tab";
	var e = ".layui-layout-admin>.layui-side>.layui-side-scroll";
	var j = ".layui-layout-admin>.layui-header";
	var b = "admin-pagetabs";
	var d = "admin-side-nav";
	var c = "theme-admin";
	var m = {
		version: "311",
		defaultTheme: "theme-admin",
		tableName: "easyweb",
		flexible: function(n) {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.flexible(n);
					return
				}
			}
			var o = h(".layui-layout-admin").hasClass("admin-nav-mini");
			if (o == !n) {
				return
			}
			if (n) {
				m.hideTableScrollBar();
				h(".layui-layout-admin").removeClass("admin-nav-mini")
			} else {
				h(".layui-layout-admin").addClass("admin-nav-mini")
			}
			m.removeNavHover()
		},
		activeNav: function(n) {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.activeNav(n);
					return
				}
			}
			if (!n) {
				n = window.location.pathname;
				n = n.substring(n.indexOf("/"))
			}
			if (n && n != "") {
				h(e + ">.layui-nav .layui-nav-item .layui-nav-child dd").removeClass("layui-this");
				h(e + ">.layui-nav .layui-nav-item").removeClass("layui-this");
				var r = h(e + '>.layui-nav a[lay-href="' + n + '"]');
				if (r && r.length > 0) {
					if (h(e + ">.layui-nav").attr("lay-accordion") == "true") {
						h(e + ">.layui-nav .layui-nav-itemed").removeClass("layui-nav-itemed")
					}
					r.parent().addClass("layui-this");
					r.parent("dd").parents(".layui-nav-child").parent().addClass("layui-nav-itemed");
					h('ul[lay-filter="' + d + '"]').addClass("layui-hide");
					var p = r.parents(".layui-nav");
					p.removeClass("layui-hide");
					h(j + ">.layui-nav>.layui-nav-item").removeClass("layui-this");
					h(j + '>.layui-nav>.layui-nav-item>a[nav-bind="' + p.attr("nav-id") + '"]').parent().addClass("layui-this");
					var o = r.offset().top + r.outerHeight() + 30 - m.getPageHeight();
					var q = 50 + 65 - r.offset().top;
					if (o > 0) {
						h(e).animate({
							"scrollTop": h(e).scrollTop() + o
						}, 100)
					} else {
						if (q > 0) {
							h(e).animate({
								"scrollTop": h(e).scrollTop() - q
							}, 100)
						}
					}
				} else {}
			} else {
				console.warn("active url is null")
			}
		},
		popupRight: function(n) {
			if (n.title == undefined) {
				n.title = false;
				n.closeBtn = false
			}
			if (n.anim == undefined) {
				n.anim = 2
			}
			if (n.fixed == undefined) {
				n.fixed = true
			}
			n.isOutAnim = false;
			n.offset = "r";
			n.shadeClose = true;
			n.area = "336px";
			n.skin = "layui-layer-adminRight";
			n.move = false;
			return m.open(n)
		},
		open: function(p) {
			if (!p.area) {
				p.area = (p.type == 2) ? ["360px", "300px"] : "360px"
			}
			if (!p.skin) {
				p.skin = "layui-layer-admin"
			}
			if (!p.offset) {
				p.offset = "35px"
			}
			if (p.fixed == undefined) {
				p.fixed = false
			}
			p.resize = p.resize != undefined ? p.resize : false;
			p.shade = p.shade != undefined ? p.shade : 0.1;
			var n = p.end;
			p.end = function() {
				k.closeAll("tips");
				n && n()
			};
			if (p.url) {
				(p.type == undefined) && (p.type = 1);
				var o = p.success;
				p.success = function(q, r) {
					m.showLoading(q, 2);
					h(q).children(".layui-layer-content").load(p.url, function() {
						o ? o(q, r) : "";
						m.removeLoading(q, false)
					})
				}
			}
			return k.open(p)
		},
		req: function(n, o, p, q) {
			m.ajax({
				url: n,
				data: o,
				type: q,
				dataType: "json",
				success: p
			})
		},
		ajax: function(o) {
			var n = o.success;
			o.success = function(p, q, s) {
				var r;
				if ("json" == o.dataType.toLowerCase()) {
					r = p
				} else {
					r = m.parseJSON(p)
				}
				r && (r = p);
				if (m.ajaxSuccessBefore(r, o.url) == false) {
					return
				}
				n(p, q, s)
			};
			o.error = function(p) {
				o.success({
					code: p.status,
					msg: p.statusText
				})
			};
			o.beforeSend = function(r) {
				var q = m.getAjaxHeaders(o.url);
				for (var p = 0; p < q.length; p++) {
					r.setRequestHeader(q[p].name, q[p].value)
				}
			};
			h.ajax(o)
		},
		parseJSON: function(p) {
			if (typeof p == "string") {
				try {
					var o = JSON.parse(p);
					if (typeof o == "object" && o) {
						return o
					}
				} catch (n) {}
			}
		},
		showLoading: function(q, p, o) {
			var n = ['<div class="ball-loader"><span></span><span></span><span></span><span></span></div>', '<div class="rubik-loader"></div>'];
			if (!q) {
				q = "body"
			}
			if (p == undefined) {
				p = 1
			}
			h(q).addClass("page-no-scroll");
			var r = h(q).children(".page-loading");
			if (r.length <= 0) {
				h(q).append('<div class="page-loading">' + n[p - 1] + "</div>");
				r = h(q).children(".page-loading")
			}
			o && r.css("background-color", "rgba(255,255,255," + o + ")");
			r.show()
		},
		removeLoading: function(o, q, n) {
			if (!o) {
				o = "body"
			}
			if (q == undefined) {
				q = true
			}
			var p = h(o).children(".page-loading");
			if (n) {
				p.remove()
			} else {
				q ? p.fadeOut() : p.hide()
			}
			h(o).removeClass("page-no-scroll")
		},
		putTempData: function(o, p) {
			var n = m.tableName + "_tempData";
			if (p != undefined && p != null) {
				layui.sessionData(n, {
					key: o,
					value: p
				})
			} else {
				layui.sessionData(n, {
					key: o,
					remove: true
				})
			}
		},
		getTempData: function(o) {
			var n = m.tableName + "_tempData";
			var p = layui.sessionData(n);
			if (p) {
				return p[o]
			} else {
				return false
			}
		},
		rollPage: function(q) {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.rollPage(q);
					return
				}
			}
			var o = h(l + ">.layui-tab-title");
			var p = o.scrollLeft();
			if ("left" === q) {
				o.animate({
					"scrollLeft": p - 120
				}, 100)
			} else {
				if ("auto" === q) {
					var n = 0;
					o.children("li").each(function() {
						if (h(this).hasClass("layui-this")) {
							return false
						} else {
							n += h(this).outerWidth()
						}
					});
					o.animate({
						"scrollLeft": n - 120
					}, 100)
				} else {
					o.animate({
						"scrollLeft": p + 120
					}, 100)
				}
			}
		},
		refresh: function(n) {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.refresh(n);
					return
				}
			}
			var p;
			if (!n) {
				p = h(l + ">.layui-tab-content>.layui-tab-item.layui-show>.admin-iframe");
				if (!p || p.length <= 0) {
					p = h(a + ">div>.admin-iframe")
				}
			} else {
				p = h(l + '>.layui-tab-content>.layui-tab-item>.admin-iframe[lay-id="' + n + '"]');
				if (!p || p.length <= 0) {
					p = h(a + ">.admin-iframe")
				}
			}
			if (p && p[0]) {
				try {
					p[0].contentWindow.location.reload(true)
				} catch (o) {
					p.attr("src", p.attr("src"))
				}
			} else {
				console.warn(n + " is not found")
			}
		},
		closeThisTabs: function(n) {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.closeThisTabs(n);
					return
				}
			}
			m.closeTabOperNav();
			var o = h(l + ">.layui-tab-title");
			if (!n) {
				if (o.find("li").first().hasClass("layui-this")) {
					k.msg("主页不能关闭", {
						icon: 2
					});
					return
				}
				o.find("li.layui-this").find(".layui-tab-close").trigger("click")
			} else {
				if (n == o.find("li").first().attr("lay-id")) {
					k.msg("主页不能关闭", {
						icon: 2
					});
					return
				}
				o.find('li[lay-id="' + n + '"]').find(".layui-tab-close").trigger("click")
			}
		},
		closeOtherTabs: function(n) {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.closeOtherTabs(n);
					return
				}
			}
			if (!n) {
				h(l + ">.layui-tab-title li:gt(0):not(.layui-this)").find(".layui-tab-close").trigger("click")
			} else {
				h(l + ">.layui-tab-title li:gt(0)").each(function() {
					if (n != h(this).attr("lay-id")) {
						h(this).find(".layui-tab-close").trigger("click")
					}
				})
			}
			m.closeTabOperNav()
		},
		closeAllTabs: function() {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.closeAllTabs();
					return
				}
			}
			h(l + ">.layui-tab-title li:gt(0)").find(".layui-tab-close").trigger("click");
			h(l + ">.layui-tab-title li:eq(0)").trigger("click");
			m.closeTabOperNav()
		},
		closeTabOperNav: function() {
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					top.layui.admin.closeTabOperNav();
					return
				}
			}
			h(".layui-icon-down .layui-nav .layui-nav-child").removeClass("layui-show")
		},
		changeTheme: function(s) {
			if (s) {
				layui.data(m.tableName, {
					key: "theme",
					value: s
				});
				if (c == s) {
					s = undefined
				}
			} else {
				layui.data(m.tableName, {
					key: "theme",
					remove: true
				})
			}
			m.removeTheme(top);
			(s && top.layui) && top.layui.link(m.getThemeDir() + s + m.getCssSuffix(), s);
			var t = top.window.frames;
			for (var p = 0; p < t.length; p++) {
				var r = t[p];
				m.removeTheme(r);
				if (s && r.layui) {
					r.layui.link(m.getThemeDir() + s + m.getCssSuffix(), s)
				}
				var q = r.frames;
				for (var o = 0; o < q.length; o++) {
					var n = q[o];
					m.removeTheme(n);
					if (s && n.layui) {
						n.layui.link(m.getThemeDir() + s + m.getCssSuffix(), s)
					}
				}
			}
		},
		removeTheme: function(n) {
			if (!n) {
				n = window
			}
			if (n.layui) {
				var o = "layuicss-theme";
				n.layui.jquery('link[id^="' + o + '"]').remove()
			}
		},
		getThemeDir: function() {
			return layui.cache.base + "theme/"
		},
		closeThisDialog: function() {
			parent.layer.close(parent.layer.getFrameIndex(window.name))
		},
		closeDialog: function(n) {
			var o = h(n).parents(".layui-layer").attr("id").substring(11);
			k.close(o)
		},
		iframeAuto: function() {
			parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name))
		},
		getPageHeight: function() {
			return document.documentElement.clientHeight || document.body.clientHeight
		},
		getPageWidth: function() {
			return document.documentElement.clientWidth || document.body.clientWidth
		},
		removeNavHover: function() {
			h(".admin-nav-hover>.layui-nav-child").css({
				"top": "auto",
				"max-height": "none",
				"overflow": "auto"
			});
			h(".admin-nav-hover").removeClass("admin-nav-hover")
		},
		setNavHoverCss: function(p) {
			var n = h(".admin-nav-hover>.layui-nav-child");
			if (p && n.length > 0 && p.offset()) {
				var r = (p.offset().top + n.outerHeight()) > window.innerHeight;
				if (r) {
					var o = p.offset().top - n.outerHeight() + p.outerHeight();
					if (o < 50) {
						var q = m.getPageHeight();
						if (p.offset().top < q / 2) {
							n.css({
								"top": "50px",
								"max-height": q - 50 + "px",
								"overflow": "auto"
							})
						} else {
							n.css({
								"top": p.offset().top,
								"max-height": q - p.offset().top,
								"overflow": "auto"
							})
						}
					} else {
						n.css("top", o)
					}
				} else {
					n.css("top", p.offset().top)
				}
				i = true
			}
		},
		getCssSuffix: function() {
			var n = ".css";
			if (m.version != undefined) {
				n += "?v=";
				if (m.version == true) {
					n += new Date().getTime()
				} else {
					n += m.version
				}
			}
			return n
		},
		hideTableScrollBar: function(o) {
			if (m.getPageWidth() > 750) {
				if (!o) {
					var n = h(l + ">.layui-tab-content>.layui-tab-item.layui-show>.admin-iframe");
					if (n.length <= 0) {
						n = h(a + ">div>.admin-iframe")
					}
					if (n.length > 0) {
						o = n[0].contentWindow
					}
				}
				if (o && o.layui && o.layui.jquery) {
					if (window.hsbTimer) {
						clearTimeout(hsbTimer)
					}
					o.layui.jquery(".layui-table-body.layui-table-main").addClass("no-scrollbar");
					window.hsbTimer = setTimeout(function() {
						if (o && o.layui && o.layui.jquery) {
							o.layui.jquery(".layui-table-body.layui-table-main").removeClass("no-scrollbar")
						}
					}, 500)
				}
			}
		},
		modelForm: function(o, s, n) {
			var r = h(o);
			r.addClass("layui-form");
			if (n) {
				r.attr("id", n);
				r.attr("lay-filter", n)
			}
			var q = r.find(".layui-layer-btn .layui-layer-btn0");
			q.attr("lay-submit", "");
			q.attr("lay-filter", s);
			var p = r.children(".layui-layer-content");
			p.find('[ew-event="closePageDialog"]').remove();
			p.find("[lay-submit]").remove()
		},
		btnLoading: function(o, p, q) {
			if (p != undefined && (typeof p == "boolean")) {
				q = p;
				p = undefined
			}(q == undefined) && (q = true);
			var n = h(o);
			if (q) {
				p && n.html(p);
				n.find(".layui-icon").addClass("layui-hide");
				n.addClass("icon-btn");
				n.prepend('<i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop ew-btn-loading"></i>');
				n.prop("disabled", "disabled")
			} else {
				n.find(".ew-btn-loading").remove();
				n.removeProp("disabled", "disabled");
				if (n.find(".layui-icon.layui-hide").length <= 0) {
					n.removeClass("icon-btn")
				}
				n.find(".layui-icon").removeClass("layui-hide");
				p && n.html(p)
			}
		},
		openSideAutoExpand: function() {
			h(".layui-layout-admin>.layui-side").off("mouseenter.openSideAutoExpand").on("mouseenter.openSideAutoExpand", function() {
				if (h(this).parent().hasClass("admin-nav-mini")) {
					m.flexible(true);
					h(this).addClass("side-mini-hover")
				}
			});
			h(".layui-layout-admin>.layui-side").off("mouseleave.openSideAutoExpand").on("mouseleave.openSideAutoExpand", function() {
				if (h(this).hasClass("side-mini-hover")) {
					m.flexible(false);
					h(this).removeClass("side-mini-hover")
				}
			})
		},
		openCellAutoExpand: function() {
			h("body").off("mouseenter.openCellAutoExpand").on("mouseenter.openCellAutoExpand", ".layui-table-view td", function() {
				h(this).find(".layui-table-grid-down").trigger("click")
			});
			h("body").off("mouseleave.openCellAutoExpand").on("mouseleave.openCellAutoExpand", ".layui-table-tips>.layui-layer-content", function() {
				h(".layui-table-tips-c").trigger("click")
			})
		},
		isTop: function() {
			return h(a).length > 0
		},
		ajaxSuccessBefore: function(n, o) {
			return true
		},
		getAjaxHeaders: function(n) {
			var o = new Array();
			return o
		}
	};
	m.events = {
		flexible: function(o) {
			var n = h(".layui-layout-admin").hasClass("admin-nav-mini");
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.jquery) {
					n = top.layui.jquery(".layui-layout-admin").hasClass("admin-nav-mini")
				}
			}
			m.flexible(n)
		},
		refresh: function() {
			m.refresh()
		},
		back: function() {
			history.back()
		},
		theme: function() {
			var n;
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					n = top.layui.admin
				} else {
					n = m
				}
			} else {
				n = m
			}
			var o = h(this).attr("data-url");
			n.popupRight({
				id: "layer-theme",
				type: 2,
				content: o ? o : "page/tpl/tpl-theme.html"
			})
		},
		note: function() {
			var n;
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					n = top.layui.admin
				} else {
					n = m
				}
			} else {
				n = m
			}
			var o = h(this).attr("data-url");
			n.popupRight({
				id: "layer-note",
				title: "便签",
				type: 2,
				closeBtn: false,
				content: o ? o : "page/tpl/tpl-note.html"
			})
		},
		message: function() {
			var n;
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					n = top.layui.admin
				} else {
					n = m
				}
			} else {
				n = m
			}
			var o = h(this).attr("data-url");
			n.popupRight({
				id: "layer-notice",
				type: 2,
				content: o ? o : "page/tpl/tpl-message.html"
			})
		},
		psw: function() {
			var n;
			if (window != top && !m.isTop()) {
				if (top.layui && top.layui.admin) {
					n = top.layui.admin
				} else {
					n = m
				}
			} else {
				n = m
			}
			var o = h(this).attr("data-url");
			n.open({
				id: "pswForm",
				type: 2,
				title: "修改密码",
				shade: 0,
				content: o ? o : "page/tpl/tpl-password.html"
			})
		},
		logout: function() {
			var n = h(this).attr("data-url");
			k.confirm("确定要退出登录吗？", {
				title: "温馨提示",
				skin: "layui-layer-admin"
			}, function() {
				location.replace(n ? n : "/")
			})
		},
		fullScreen: function(t) {
			var v = "layui-icon-screen-full",
				p = "layui-icon-screen-restore";
			var n = h(this).find("i");
			var s = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
			if (s) {
				var r = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
				if (r) {
					r.call(document)
				} else {
					if (window.ActiveXObject) {
						var u = new ActiveXObject("WScript.Shell");
						u && u.SendKeys("{F11}")
					}
				}
				n.addClass(v).removeClass(p)
			} else {
				var o = document.documentElement;
				var q = o.requestFullscreen || o.webkitRequestFullscreen || o.mozRequestFullScreen || o.msRequestFullscreen;
				if (q) {
					q.call(o)
				} else {
					if (window.ActiveXObject) {
						var u = new ActiveXObject("WScript.Shell");
						u && u.SendKeys("{F11}")
					}
				}
				n.addClass(p).removeClass(v)
			}
		},
		leftPage: function() {
			m.rollPage("left")
		},
		rightPage: function() {
			m.rollPage()
		},
		closeThisTabs: function() {
			m.closeThisTabs()
		},
		closeOtherTabs: function() {
			m.closeOtherTabs()
		},
		closeAllTabs: function() {
			m.closeAllTabs()
		},
		closeDialog: function() {
			m.closeThisDialog()
		},
		closePageDialog: function() {
			m.closeDialog(this)
		}
	};
	h(document).on("click", "*[ew-event]", function() {
		var n = h(this).attr("ew-event");
		var o = m.events[n];
		o && o.call(this, h(this))
	});
	h(document).on("mouseenter", "*[lay-tips]", function() {
		var n = h(this).attr("lay-tips");
		var o = h(this).attr("lay-direction");
		var p = h(this).attr("lay-bg");
		k.tips(n, this, {
			tips: [o || 3, p || "#333333"],
			time: -1
		})
	}).on("mouseleave", "*[lay-tips]", function() {
		k.closeAll("tips")
	});
	var i = false;
	h(document).on("mouseenter", ".layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a", function() {
		if (m.getPageWidth() > 750) {
			var p = h(this);
			h(".admin-nav-hover>.layui-nav-child").css("top", "auto");
			h(".admin-nav-hover").removeClass("admin-nav-hover");
			p.parent().addClass("admin-nav-hover");
			var n = h(".admin-nav-hover>.layui-nav-child");
			if (n.length > 0) {
				m.setNavHoverCss(p)
			} else {
				var o = p.find("cite").text();
				k.tips(o, p, {
					tips: [2, "#333333"],
					time: -1
				})
			}
		}
	}).on("mouseleave", ".layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a", function() {
		k.closeAll("tips")
	});
	h(document).on("mouseleave", ".layui-layout-admin.admin-nav-mini .layui-side", function() {
		i = false;
		setTimeout(function() {
			if (!i) {
				m.removeNavHover()
			}
		}, 500)
	});
	h(document).on("mouseenter", ".layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item.admin-nav-hover .layui-nav-child", function() {
		i = true
	});
	h(document).on("click", "*[ew-href]", function() {
		var n = h(this).attr("ew-href");
		var o = h(this).attr("ew-title");
		o || (o = h(this).text());
		if (top.layui && top.layui.index) {
			top.layui.index.openTab({
				title: o ? o : "",
				url: n
			})
		} else {
			location.href = o
		}
	});
	var g = layui.data(m.tableName);
	if (g && g.theme) {
		(g.theme == c) || layui.link(m.getThemeDir() + g.theme + m.getCssSuffix(), g.theme)
	} else {
		if (c != m.defaultTheme) {
			layui.link(m.getThemeDir() + m.defaultTheme + m.getCssSuffix(), m.defaultTheme)
		}
	}
	if (!layui.contextMenu) {
		h(document).off("click.ctxMenu").on("click.ctxMenu", function() {
			var p = top.window.frames;
			for (var n = 0; n < p.length; n++) {
				var o = p[n];
				(o.layui && o.layui.jquery) && o.layui.jquery("body>.ctxMenu").remove()
			}(top.layui && top.layui.jquery) && top.layui.jquery("body>.ctxMenu").remove()
		})
	}
	m.chooseLocation = function(s) {
		var o = s.title;
		var w = s.onSelect;
		var q = s.needCity;
		var x = s.center;
		var A = s.defaultZoom;
		var t = s.pointZoom;
		var v = s.keywords;
		var z = s.pageSize;
		var r = s.mapJsUrl;
		(o == undefined) && (o = "选择位置");
		(A == undefined) && (A = 11);
		(t == undefined) && (t = 17);
		(v == undefined) && (v = "");
		(z == undefined) && (z = 30);
		(r == undefined) && (r = "https://webapi.amap.com/maps?v=1.4.14&key=006d995d433058322319fa797f2876f5");
		var B = false,
			y;
		var u = function(D, C) {
				AMap.service(["AMap.PlaceSearch"], function() {
					var F = new AMap.PlaceSearch({
						type: "",
						pageSize: z,
						pageIndex: 1
					});
					var E = [C, D];
					F.searchNearBy(v, E, 1000, function(H, G) {
						if (H == "complete") {
							var K = G.poiList.pois;
							var L = "";
							for (var J = 0; J < K.length; J++) {
								var I = K[J];
								if (I.location != undefined) {
									L += '<div data-lng="' + I.location.lng + '" data-lat="' + I.location.lat + '" class="ew-map-select-search-list-item">';
									L += '     <div class="ew-map-select-search-list-item-title">' + I.name + "</div>";
									L += '     <div class="ew-map-select-search-list-item-address">' + I.address + "</div>";
									L += '     <div class="ew-map-select-search-list-item-icon-ok layui-hide"><i class="layui-icon layui-icon-ok-circle"></i></div>';
									L += "</div>"
								}
							}
							h("#ew-map-select-pois").html(L)
						}
					})
				})
			};
		var n = function() {
				var C = {
					resizeEnable: true,
					zoom: A
				};
				x && (C.center = x);
				var D = new AMap.Map("ew-map-select-map", C);
				D.on("complete", function() {
					var E = D.getCenter();
					u(E.lat, E.lng)
				});
				D.on("moveend", function() {
					if (B) {
						B = false
					} else {
						h("#ew-map-select-tips").addClass("layui-hide");
						h("#ew-map-select-center-img").removeClass("bounceInDown");
						setTimeout(function() {
							h("#ew-map-select-center-img").addClass("bounceInDown")
						});
						var E = D.getCenter();
						u(E.lat, E.lng)
					}
				});
				h("#ew-map-select-pois").off("click").on("click", ".ew-map-select-search-list-item", function() {
					h("#ew-map-select-tips").addClass("layui-hide");
					h("#ew-map-select-pois .ew-map-select-search-list-item-icon-ok").addClass("layui-hide");
					h(this).find(".ew-map-select-search-list-item-icon-ok").removeClass("layui-hide");
					h("#ew-map-select-center-img").removeClass("bounceInDown");
					setTimeout(function() {
						h("#ew-map-select-center-img").addClass("bounceInDown")
					});
					var G = h(this).data("lng");
					var H = h(this).data("lat");
					var F = h(this).find(".ew-map-select-search-list-item-title").text();
					var E = h(this).find(".ew-map-select-search-list-item-address").text();
					y = {
						name: F,
						address: E,
						lat: H,
						lng: G
					};
					B = true;
					D.setZoomAndCenter(t, [G, H])
				});
				h("#ew-map-select-btn-ok").click(function() {
					if (y == undefined) {
						k.msg("请点击位置列表选择", {
							icon: 2,
							anim: 6
						})
					} else {
						if (w) {
							if (q) {
								var E = k.load(2);
								D.setCenter([y.lng, y.lat]);
								D.getCity(function(F) {
									k.close(E);
									y.city = F;
									m.closeDialog("#ew-map-select-btn-ok");
									w(y)
								})
							} else {
								m.closeDialog("#ew-map-select-btn-ok");
								w(y)
							}
						} else {
							m.closeDialog("#ew-map-select-btn-ok")
						}
					}
				});
				h("#ew-map-select-input-search").off("input").on("input", function() {
					var E = h(this).val();
					if (!E) {
						h("#ew-map-select-tips").html("");
						h("#ew-map-select-tips").addClass("layui-hide")
					}
					AMap.plugin("AMap.Autocomplete", function() {
						var F = new AMap.Autocomplete({
							city: "全国"
						});
						F.search(E, function(I, H) {
							if (H.tips) {
								var G = H.tips;
								var K = "";
								for (var J = 0; J < G.length; J++) {
									var L = G[J];
									if (L.location != undefined) {
										K += '<div data-lng="' + L.location.lng + '" data-lat="' + L.location.lat + '" class="ew-map-select-search-list-item">';
										K += '     <div class="ew-map-select-search-list-item-icon-search"><i class="layui-icon layui-icon-search"></i></div>';
										K += '     <div class="ew-map-select-search-list-item-title">' + L.name + "</div>";
										K += '     <div class="ew-map-select-search-list-item-address">' + L.address + "</div>";
										K += "</div>"
									}
								}
								h("#ew-map-select-tips").html(K);
								if (G.length == 0) {
									h("#ew-map-select-tips").addClass("layui-hide")
								} else {
									h("#ew-map-select-tips").removeClass("layui-hide")
								}
							} else {
								h("#ew-map-select-tips").html("");
								h("#ew-map-select-tips").addClass("layui-hide")
							}
						})
					})
				});
				h("#ew-map-select-input-search").off("blur").on("blur", function() {
					var E = h(this).val();
					if (!E) {
						h("#ew-map-select-tips").html("");
						h("#ew-map-select-tips").addClass("layui-hide")
					}
				});
				h("#ew-map-select-input-search").off("focus").on("focus", function() {
					var E = h(this).val();
					if (E) {
						h("#ew-map-select-tips").removeClass("layui-hide")
					}
				});
				h("#ew-map-select-tips").off("click").on("click", ".ew-map-select-search-list-item", function() {
					h("#ew-map-select-tips").addClass("layui-hide");
					var E = h(this).data("lng");
					var F = h(this).data("lat");
					y = undefined;
					D.setZoomAndCenter(t, [E, F])
				})
			};
		var p = '<div class="ew-map-select-tool" style="position: relative;">';
		p += '        搜索：<input id="ew-map-select-input-search" class="layui-input icon-search inline-block" style="width: 190px;" placeholder="输入关键字搜索" autocomplete="off" />';
		p += '        <button id="ew-map-select-btn-ok" class="layui-btn icon-btn pull-right" type="button"><i class="layui-icon">&#xe605;</i>确定</button>';
		p += '        <div id="ew-map-select-tips" class="ew-map-select-search-list layui-hide">';
		p += "        </div>";
		p += "   </div>";
		p += '   <div class="layui-row ew-map-select">';
		p += '        <div class="layui-col-sm7 ew-map-select-map-group" style="position: relative;">';
		p += '             <div id="ew-map-select-map"></div>';
		p += '             <i id="ew-map-select-center-img2" class="layui-icon layui-icon-add-1"></i>';
		p += '             <img id="ew-map-select-center-img" src="https://3gimg.qq.com/lightmap/components/locationPicker2/image/marker.png"/>';
		p += "        </div>";
		p += '        <div id="ew-map-select-pois" class="layui-col-sm5 ew-map-select-search-list">';
		p += "        </div>";
		p += "   </div>";
		m.open({
			id: "ew-map-select",
			type: 1,
			title: o,
			area: "750px",
			content: p,
			success: function(C, D) {
				h(C).children(".layui-layer-content").css("overflow", "visible");
				m.showLoading(C);
				if (undefined == window.AMap) {
					h.getScript(r, function() {
						n();
						m.removeLoading(C)
					})
				} else {
					n();
					m.removeLoading(C)
				}
			}
		})
	};
	m.cropImg = function(q) {
		var o = "image/jpeg";
		var v = q.aspectRatio;
		var w = q.imgSrc;
		var t = q.imgType;
		var r = q.onCrop;
		var s = q.limitSize;
		var u = q.acceptMime;
		var p = q.exts;
		var n = q.title;
		(v == undefined) && (v = 1 / 1);
		(n == undefined) && (n = "裁剪图片");
		t && (o = t);
		layui.use(["cropper", "upload"], function() {
			var x = layui.upload;

			function y() {
				var B = h("#ew-crop-img");
				var C = {
					elem: "#ew-crop-img-upload",
					auto: false,
					drag: false,
					choose: function(D) {
						D.preview(function(F, G, E) {
							o = G.type;
							if (!w) {
								w = E;
								h("#ew-crop-img").attr("src", E);
								y()
							} else {
								B.cropper("destroy").attr("src", E).cropper(A)
							}
						})
					}
				};
				(s != undefined) && (C.size = s);
				(u != undefined) && (C.acceptMime = u);
				(p != undefined) && (C.exts = p);
				x.render(C);
				if (!w) {
					h("#ew-crop-img-upload").trigger("click");
					return
				}
				var A = {
					aspectRatio: v,
					preview: "#ew-crop-img-preview"
				};
				B.cropper(A);
				h(".ew-crop-tool").on("click", "[data-method]", function() {
					var E = B.data("cropper");
					var F = h(this).data(),
						G, D;
					if (!E || !F.method) {
						return
					}
					F = h.extend({}, F);
					G = E.cropped;
					switch (F.method) {
					case "rotate":
						if (G && A.viewMode > 0) {
							B.cropper("clear")
						}
						break;
					case "getCroppedCanvas":
						if (o === "image/jpeg") {
							if (!F.option) {
								F.option = {}
							}
							F.option.fillColor = "#fff"
						}
						break
					}
					D = B.cropper(F.method, F.option, F.secondOption);
					switch (F.method) {
					case "rotate":
						if (G && A.viewMode > 0) {
							B.cropper("crop")
						}
						break;
					case "scaleX":
					case "scaleY":
						h(this).data("option", -F.option);
						break;
					case "getCroppedCanvas":
						if (D) {
							r && r(D.toDataURL(o));
							m.closeDialog("#ew-crop-img")
						} else {
							k.msg("裁剪失败", {
								icon: 2,
								anim: 6
							})
						}
						break
					}
				})
			}
			var z = '<div class="layui-row">';
			z += '        <div class="layui-col-sm8" style="min-height: 9rem;">';
			z += '             <img id="ew-crop-img" src="' + (w ? w : "") + '" style="max-width:100%;" />';
			z += "        </div>";
			z += '        <div class="layui-col-sm4 layui-hide-xs" style="padding: 0 20px;text-align: center;">';
			z += '             <div id="ew-crop-img-preview" style="width: 100%;height: 9rem;overflow: hidden;display: inline-block;border: 1px solid #dddddd;"></div>';
			z += "        </div>";
			z += "   </div>";
			z += '   <div class="text-center ew-crop-tool" style="padding: 15px 10px 5px 0;">';
			z += '        <div class="layui-btn-group" style="margin-bottom: 10px;margin-left: 10px;">';
			z += '             <button title="放大" data-method="zoom" data-option="0.1" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-add-1"></i></button>';
			z += '             <button title="缩小" data-method="zoom" data-option="-0.1" class="layui-btn icon-btn" type="button"><span style="display: inline-block;width: 12px;height: 2.5px;background: rgba(255, 255, 255, 0.9);vertical-align: middle;margin: 0 4px;"></span></button>';
			z += "        </div>";
			z += '        <div class="layui-btn-group layui-hide-xs" style="margin-bottom: 10px;">';
			z += '             <button title="向左旋转" data-method="rotate" data-option="-45" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh-1" style="transform: rotateY(180deg) rotate(40deg);display: inline-block;"></i></button>';
			z += '             <button title="向右旋转" data-method="rotate" data-option="45" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh-1" style="transform: rotate(30deg);display: inline-block;"></i></button>';
			z += "        </div>";
			z += '        <div class="layui-btn-group" style="margin-bottom: 10px;">';
			z += '             <button title="左移" data-method="move" data-option="-10" data-second-option="0" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-left"></i></button>';
			z += '             <button title="右移" data-method="move" data-option="10" data-second-option="0" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-right"></i></button>';
			z += '             <button title="上移" data-method="move" data-option="0" data-second-option="-10" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-up"></i></button>';
			z += '             <button title="下移" data-method="move" data-option="0" data-second-option="10" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-down"></i></button>';
			z += "        </div>";
			z += '        <div class="layui-btn-group" style="margin-bottom: 10px;">';
			z += '             <button title="左右翻转" data-method="scaleX" data-option="-1" class="layui-btn icon-btn" type="button" style="position: relative;width: 41px;"><i class="layui-icon layui-icon-triangle-r" style="position: absolute;left: 9px;top: 0;transform: rotateY(180deg);font-size: 16px;"></i><i class="layui-icon layui-icon-triangle-r" style="position: absolute; right: 3px; top: 0;font-size: 16px;"></i></button>';
			z += '             <button title="上下翻转" data-method="scaleY" data-option="-1" class="layui-btn icon-btn" type="button" style="position: relative;width: 41px;"><i class="layui-icon layui-icon-triangle-d" style="position: absolute;left: 11px;top: 6px;transform: rotateX(180deg);line-height: normal;font-size: 16px;"></i><i class="layui-icon layui-icon-triangle-d" style="position: absolute; left: 11px; top: 14px;line-height: normal;font-size: 16px;"></i></button>';
			z += "        </div>";
			z += '        <div class="layui-btn-group" style="margin-bottom: 10px;">';
			z += '             <button title="重新开始" data-method="reset" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh"></i></button>';
			z += '             <button title="选择图片" id="ew-crop-img-upload" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-upload-drag"></i></button>';
			z += "        </div>";
			z += '        <button data-method="getCroppedCanvas" data-option="{ &quot;maxWidth&quot;: 4096, &quot;maxHeight&quot;: 4096 }" class="layui-btn icon-btn" type="button" style="margin-left: 10px;margin-bottom: 10px;"><i class="layui-icon">&#xe605;</i>完成</button>';
			z += "   </div>";
			m.open({
				title: n,
				area: "665px",
				type: 1,
				content: z,
				success: function(A, B) {
					h(A).children(".layui-layer-content").css("overflow", "visible");
					y()
				}
			})
		})
	};
	f("admin", m)
});