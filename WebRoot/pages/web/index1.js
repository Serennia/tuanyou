/*from tccdn minify at 2014-12-12 18:11:19,file：/cn/c/c/2014/tc_2_5.js,file：/cn/c/c/common-aspect.1.1.2.js?v=2014120101*/
function getCookieDomain() {
    return location.hostname.match(/^(\d+\.\d+\.\d+\.\d+)|(localhost)$/) ? location.hostname: /.*(\..*\..*)$/.exec(location.hostname)[1]
} !
function() {
    function jsonParse(str) {
        return "" === str ? str: eval("(" + str + ")")
    }
    var ajax = fish.ajax;
    fish.ajax = function(a) {
        if ("script" !== a.type) {
            var b = fish.lang.extend({},
            a);
            if ("jsonp" === a.type) {
                var c;
                c = -1 === a.url.indexOf("?") ? "?": "&",
                b.url = a.url + c + "_dAjax=" + ("string" == typeof a.jsonp && "" !== a.jsonp ? a.jsonp: "callback")
            } else {
                b.type = "text"
            }
            b.fn = function(b) {
                var c = "ipdenfense";
                return "string" == typeof b && b.substring(0, c.length) === c ? void(window.location.href = b.substring(c.length + 1)) : ("json" === a.type && (b = jsonParse(b)), void(a.fn && a.fn(b)))
            },
            ajax(b)
        } else {
            ajax(a)
        }
    }
} (),
function() {
    function c() {}
    function d(a) {
        function s(f) {
            var g, h;
            if (!f.pageX && event.clientX) {
                var i = document.documentElement,
                j = document.body;
                g = event.clientX + (i && i.scrollLeft || j && j.scrollLeft || 0) - (i && i.clientLeft || j && j.clientLeft || 0),
                h = event.clientY + (i && i.scrollTop || j && j.scrollTop || 0) - (i && i.clientTop || j && j.clientTop || 0)
            } else {
                g = f.pageX,
                h = f.pageY
            }
            B.push({
                x: g,
                y: h
            }),
            B.length > F && B.shift()
        }
        function t() {
            D && clearTimeout(D),
            E.exitMenu(this) && A && (E.deactivate(A), A = null)
        }
        function u() {
            D && clearTimeout(D),
            E.enter(this),
            x(this)
        }
        function v() {
            E.exit(this)
        }
        function w(b) {
            b !== A && (A && E.deactivate(A), E.activate(b), A = b)
        }
        function x(e) {
            var f = H();
            f ? D = setTimeout(function() {
                x(e)
            },
            f) : w(e)
        }
        function y(e) {
            var f = e.offsetTop,
            g = e.offsetLeft,
            h = {
                width: e.offsetWidth,
                height: e.offsetHeight
            };
            for (e = e.offsetParent; e;) {
                f += e.offsetTop,
                g += e.offsetLeft,
                e = e.offsetParent
            }
            return h.top = f,
            h.left = g,
            h
        }
        var z = fish.one(this),
        A = null,
        B = [],
        C = null,
        D = null,
        E = fish.lang.extend({
            rowSelector: "li",
            submenuSelector: "*",
            submenuDirection: "right",
            tolerance: 75,
            enter: c,
            exit: c,
            activate: c,
            deactivate: c,
            exitMenu: c
        },
        a),
        F = 3,
        G = 300,
        H = function() {
            function i(e, f) {
                return (f.y - e.y) / (f.x - e.x)
            }
            if (!A || -1 === fish.all(E.submenuSelector, z).indexOf(A)) {
                return 0
            }
            var j = y(z[0]),
            k = {
                x: j.left,
                y: j.top - E.tolerance
            },
            l = {
                x: j.left + z.width(),
                y: k.y
            },
            m = {
                x: j.left,
                y: j.top + z.height() + E.tolerance
            },
            o = {
                x: j.left + z.width(),
                y: m.y
            },
            q = B[B.length - 1],
            I = B[0];
            if (!q) {
                return 0
            }
            if (I || (I = q), I.x < j.left || I.x > o.x || I.y < j.top || I.y > o.y) {
                return 0
            }
            if (C && q.x == C.x && q.y == C.y) {
                return 0
            }
            var J = l,
            K = o;
            "left" == E.submenuDirection ? (J = m, K = k) : "below" == E.submenuDirection ? (J = o, K = m) : "above" == E.submenuDirection && (J = k, K = l);
            var L = i(q, J),
            M = i(q, K),
            N = i(I, J),
            O = i(I, K);
            return N > L && M > O ? (C = q, G) : (C = null, 0)
        };
        z.hover(c, t),
        fish.all(E.rowSelector, z).hover(u, v),
        fish.one(document).on("mousemove", s)
    }
    fish.extend({
        menuAim: function(b) {
            return this.each(function() {
                d.call(this, b)
            }),
            this
        }
    })
} ();
var strHost = getCookieDomain(); !
function() {
    function g(c) {
        var f = i(),
        p = encodeURIComponent(document.referrer),
        q = "RefId=" + f,
        r = "CNSEInfo",
        s = (new Date, l());
        if (!c && "gb2312" === s[1]) {
            var t = document.createElement("script");
            return t.src = "http://www" + strHost + "/AjaxHelper/Gb2312ToUtf8.ashx?words=" + s[2] + "&callback=reDumpRefid",
            void document.getElementsByTagName("head")[0].appendChild(t)
        }
        s[0] = encodeURIComponent(s[0]),
        s[2] = c ? c: s[2],
        (p.indexOf(".17u.com") > -1 || p.indexOf(".17u.cn") > -1 || p.indexOf(".ly.com") > -1 || p.indexOf(".LY.com") > -1 || p.indexOf("localhost") > -1 || p.indexOf("192.168.") > -1 || p.indexOf("172.16.") > -1 || p.indexOf(".tongcheng.com") > -1 || "" === p) && (s[0] = encodeURIComponent(fish.cookie.get("CNSEInfo", "SEFrom")), s[2] = encodeURIComponent(fish.cookie.get("CNSEInfo", "SEKeyWords")), p = encodeURIComponent(fish.cookie.get("CNSEInfo", "RefUrl"))),
        p = void 0 == p ? "": p,
        p = "undefined" == p ? "": p,
        s[0] = void 0 == s[0] ? "": s[0],
        s[0] = "undefined" == s[0] ? "": s[0],
        s[2] = void 0 == s[2] ? "": s[2],
        s[2] = "undefined" == s[2] ? "": s[2];
        var u = "SEFrom=" + s[0],
        v = "SEKeyWords=" + s[2],
        w = "RefUrl=" + p,
        x = q + "&" + u + "&" + v + "&" + w,
        y = 0,
        z = q + "&" + u + "&" + v;
        if (f + "" == "6076168" && (y = 1), fish.cookie.set({
            name: r,
            value: x,
            path: "/",
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: r,
            value: x,
            path: "/",
            domain: strHost,
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: r,
            value: x,
            days: -1,
            path: "/",
            encode: !1
        }), fish.cookie.set({
            name: r,
            value: x,
            path: "/",
            domain: strHost,
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: "17uCNRefId",
            value: z,
            path: "/",
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: "17uCNRefId",
            value: z,
            path: "/",
            domain: strHost,
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: "17uCNRefId",
            value: z,
            days: -1,
            path: "/",
            encode: !1
        }), fish.cookie.set({
            name: "17uCNRefId",
            value: z,
            path: "/",
            domain: strHost,
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: "TicketSEInfo",
            value: z,
            path: "/",
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: "TicketSEInfo",
            value: z,
            path: "/",
            domain: strHost,
            encode: !1,
            days: y
        }), fish.cookie.set({
            name: "TicketSEInfo",
            value: z,
            days: -1,
            path: "/",
            encode: !1
        }), fish.cookie.set({
            name: "TicketSEInfo",
            value: z,
            path: "/",
            domain: strHost,
            encode: !1,
            days: y
        }), s[2] && !fish.cookie.get(r)) {
            var t = document.createElement("script");
            return t.src = "http://www" + strHost + "/AjaxHelper/Gb2312ToUtf8.ashx?words=" + s[2] + "&callback=reDumpRefid",
            void document.getElementsByTagName("head")[0].appendChild(t)
        }
    }
    function h(a) {
        g(a && a.words && "" !== a.words ? a.words: "")
    }
    function i() {
        var e, m, n = window.location.href.toLowerCase();
        e = fish.cookie.get("CNSEInfo", "RefId");
        var o = n.indexOf("#"),
        p = n.indexOf("?");
        if (o > -1) {
            m = n.substring(o + 1);
            var q = k(m, "refid");
            "" !== q && "undefined" !== q && void 0 != q && (e = q)
        }
        if (p > -1) {
            m = o > -1 && o > p ? n.substring(p + 1, o) : n.substring(p + 1);
            var q = k(m, "refid");
            "" !== q && "undefined" !== q && void 0 != q && (e = q)
        }
        return e = void 0 == e ? "0": e,
        e = "undefined" == e ? "0": e,
        "" === e ? "0": e
    }
    function j() {
        var b = fish.cookie.get("CNMember", "MemberId");
        return b = void 0 == b ? "0": b,
        b = "undefined" == b ? "0": b,
        "" === b ? "0": b
    }
    function k(e, f) {
        for (var m = e.split(/&|\?|#/g), n = 0; n < m.length; n++) {
            if (m[n].substring(0, f.length + 1) == f + "=") {
                return m[n].substring(f.length + 1)
            }
        }
        return ""
    }
    function l() {
        var m = new Array("", "", "");
        if (document.referrer && "" != document.referrer) {
            var n, o, p, q = document.referrer;
            if (q.match(new RegExp("baidu\\."))) {
                if (n = "baidu", o = "gb2312", q.match(new RegExp("(\\?|\\&)(wd|word)\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)(wd|word)\\=([^\\&]+)"));
                    p = r[3],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=utf\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("google\\."))) {
                if (n = "google", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(gb2312)|(gb)")) && (o = "gb2312")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("yahoo\\."))) {
                if (n = "yahoo", o = "utf-8", q.match(new RegExp("(\\?|\\&)p\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)p\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ei)\\=(GBK|gbk)")) && (o = "gbk")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("bing\\."))) {
                if (n = "bing", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2]
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("soso\\."))) {
                if (n = "soso", o = "gb2312", q.match(new RegExp("(\\?|\\&)w\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)w\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(UTF|utf)\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("sogou\\."))) {
                if (n = "sogou", o = "utf-8", q.match(new RegExp("(\\?|\\&)query\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)query\\=([^\\&]+)"));
                    p = r[2]
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("iask\\."))) {
                if (n = "iask", o = "gb2312", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2]
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("youdao\\."))) {
                if (n = "youdao", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(gb2312)|(gb)")) && (o = "gb2312")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("so\\.360"))) {
                if (n = "so.360", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(UTF|utf)\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("www\\.so\\."))) {
                if (n = "so.com", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(UTF|utf)\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("jike\\."))) {
                if (n = "jike", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(UTF|utf)\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("panguso\\."))) {
                if (n = "panguso", o = "utf-8", q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)q\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(UTF|utf)\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
            if (q.match(new RegExp("zhongsou\\."))) {
                if (n = "zhongsou", o = "gb231", q.match(new RegExp("(\\?|\\&)w\\=([^\\&]+)"))) {
                    var r = q.match(new RegExp("(\\?|\\&)w\\=([^\\&]+)"));
                    p = r[2],
                    q.match(new RegExp("(\\?|\\&)(ie)\\=(UTF|utf)\\-8")) && (o = "utf-8")
                }
                return m[0] = n,
                m[1] = o,
                m[2] = p,
                m
            }
        }
        return m
    }
    window.reDumpRefid = h,
    window.getRefid = i,
    window.getMemberId = j,
    window.dumpRefid = g,
    g()
} (),
function() {
    function b() {
        var c = location.hostname.match(/\.[^\.]*\.(com|cn|net)$/);
        return c ? c[0].substring(1) : location.hostname
    }
    fish.extend({
        getRootDomain: b
    })
} (),
function() {
    var f = {
        state: 200
    },
    g = fish.cookie.get("cnUser", "userid");
    if (g) {
        f = {
            state: 100
        };
        var h = "",
        i = fish.cookie.get("cnUser", "nickName") || fish.cookie.get("passport_login_state", "partner_loginname");
        i && (h += '<a class="username" rel="nofollow" href="http://member.ly.com/">' + i + "</a><span>，您好</span>");
        var j = fish.cookie.get("cnUser", "level");
        j && (h += '<a class="member-level level' + (parseInt(j, 10) + 1) + '" rel="nofollow" target="_blank" href="http://www.ly.com/vip/rights/" title="点击查看我的特权"></a>'),
        h += '<a class="loginout" rel="nofollow" href="http://www.ly.com/AjaxHelper/TopLoginHandler.aspx?action=exitnew" title="退出帐号"><span>退出</span></a><a class="fast-pay" rel="nofollow" href="http://member.ly.com/">订单快速支付</a>',
        document.getElementById("topLogin").innerHTML = h,
        fish.one("#topLogin .loginout").on("click",
        function() {
            fish.cookie.remove("FakeLogin", {
                domain: fish.getRootDomain(),
                path: "/"
            })
        })
    }
    window.loginState = f
} (),
function() {
    "gonglue" === fish.one("#channel").val() && fish.all("#topMyTc div, #menuMyTc div").html("bottom", '<a rel="nofollow" href="http://go.ly.com/user/">我的社区</a>');
    var g = fish.all("#menuNav .current .submenu-nav");
    if (g.length) {
        g = fish.all("a", g);
        for (var h, i = location.href,
        j = 0,
        k = 1,
        l = g.length; l > k; k++) {
            if (h = g[k], 0 === i.indexOf(h.href) && !fish.one(h).hasClass("order")) {
                j = k;
                break
            }
        }
        fish.one(g[j]).addClass("current")
    }
    fish.all("#topMyTc, #menuMyTc").hover(function() {
        var c = fish.one("div", this),
        d = fish.all("a", c);
        c[0].style.height = fish.one(d[0]).height() * d.length + 18 + "px",
        fish.one(this).addClass("open")
    },
    function() {
        fish.dom("div", this).style.height = "0",
        fish.one(this).removeClass("open")
    })
} (),
function() {
    function d() { ("public" === e || "chujingtravel" === e) && (fish.one(window).width() < 1200 && fish.one(window).width() > 0 ? fish.one(document.body).hasClass("w960") || fish.one(document.body).addClass("w960") : fish.one(document.body).hasClass("w960") && fish.one(document.body).removeClass("w960"))
    }
    d();
    var e = fish.one("#channel").html(),
    f = 0;
    fish.one(window).on("resize",
    function() { (0 == f || f != fish.one(window).width()) && (f = fish.one(window).width(), d())
    })
} (),
function() {
    function h() {
        fish.ajax({
            url: n + "&date=" + new Date,
            type: "jsonp",
            fn: function(d) {
                if (200 === d.state) {
                    var e, f = fish.all("#servicePhone p");
                    switch (d.telephoneShowType) {
                    case "2":
                        e = f[0],
                        e && (e.style.display = "none");
                        break;
                    default:
                        e = f[0],
                        e && (e.innerHTML = "国内电话：" + d.telephone),
                        e = document.getElementById("hotNum"),
                        e && (e.innerHTML = d.telephone)
                    }
                }
            }
        })
    }
    function i(f) {
        for (var g = f.split("split|,|split"), o = "", p = 0, q = g.length; q > p && (o = g[p].split("split|:|split"), 2 !== o.length); p++) {}
        return o
    }
    fish.all("#mobileTc, #weixinTc").hover(function() {
        fish.one(this).addClass("open")
    },
    function() {
        fish.one(this).removeClass("open")
    });
    var j = fish.getRootDomain(),
    k = fish.one("#AjaxRequestPath").val();
    k ? -1 === k.indexOf("www") && (k = "http://www.ly.com" + k) : k = "http://www." + j + "/AjaxHelper/TopLoginHandler.aspx?channel=Index";
    var l = "&asyncRefid=" + getRefid() + "&asyncUniqueKey=" + fish.one("#TcPmsUniqueKey").val(),
    m = k + "&action=getBulletin" + l,
    n = k + "&action=getTelephone" + l;
    fish.ajax({
        url: m + "&date=" + new Date,
        type: "jsonp",
        fn: function(c) {
            if (c && 100 === c.state) {
                var d = document.getElementById("bulletin");
                d && (d.innerHTML = c.bulletin)
            }
        }
    }),
    fish.all("#servicePhone").hover(function() {
        h.hasExecuted || (h(), h.hasExecuted = !0),
        fish.one(this).addClass("open")
    },
    function() {
        fish.one(this).removeClass("open")
    }),
    fish.one("#headerLink").length && fish.ajax({
        url: "http://www." + j + "/AjaxHelper/GetIpLocAjax.aspx?action=getheadadbyipnew&idlist=head-ad&TcPmsUniqueKey=cndefault2013asynchronous",
        type: "jsonp",
        fn: function(b) {
            b && "200" === b.state && fish.one("#headerLink").html(i(b.content)[1]).removeClass("none")
        }
    })
} (),
function() {
    function b(c) {
        if (c = fish.one(c), c.hasClass("current")) {
            return fish.one("#menuNav .current .submenu-nav").removeClass("closed"),
            void fish.one("#submenuNavBg").removeClass("hover")
        }
        c.next().hasClass("current") ? c.addClass("current-left") : c.previous().hasClass("current") && c.addClass("current-right"),
        c.addClass("hover");
        var d = fish.one("#submenuNavBg");
        fish.one(".submenu-nav", c).length ? (fish.one("#menuNav .current .submenu-nav").addClass("closed"), d.hasClass("current") || d.hasClass("hover") || d.addClass("hover")) : (fish.one("#menuNav .current .submenu-nav").removeClass("closed"), !d.hasClass("current") && d.hasClass("hover") && d.removeClass("hover"))
    }
    fish.all("#menuNav").menuAim({
        submenuDirection: "below",
        tolerance: 0,
        activate: b,
        deactivate: function(c) {
            fish.one(c).removeClass("hover")
        },
        exitMenu: function() {
            return fish.one("#menuNav .current .submenu-nav").removeClass("closed"),
            fish.one("#submenuNavBg").removeClass("hover"),
            !0
        }
    })
} (),
fish.ready(function() {
    if (fish.cookie.get("NewProvinceId")) {
        for (var d = fish.cookie.get("NewProvinceId"), e = fish.all("#hotCityList a"), f = 0; f < e.length; f += 1) {
            fish.one(e[f]).attr("cityid") == d && fish.one("#locationNow .nowLoc").html(fish.one(e[f]).html())
        }
    }
    fish.all("#locationNow,#hotCityList").hover(function() {
        fish.one("#hotCityList").css("display:block"),
        fish.one("#locationNow span.morSelfIco").removeClass("mytcDown").addClass("mytcUp")
    },
    function() {
        fish.one("#hotCityList").css("display:none"),
        fish.one("#locationNow span.morSelfIco").removeClass("mytcUp").addClass("mytcDown")
    }),
    fish.all("#hotCityList a").on("click",
    function() {
        var b = fish.one(this).attr("cityID");
        fish.one(this).html();
        return fish.cookie.set({
            name: "NewProvinceId",
            path: "/",
            value: b,
            domain: strHost,
            days: 7
        }),
        location.href.indexOf(location.host + "/zizhuyou") >= 0 ? window.location.assign("/zizhuyou") : window.location.reload(),
        !1
    }),
    fish.all("#showRecomMore").on("click",
    function() {
        "mytcDown" == fish.dom("span", this).className ? (fish.all("#hrLinklist").css("height:auto;"), fish.dom("span", this).className = "mytcUp") : (fish.all("#hrLinklist").css("height:20px;"), fish.dom("span", this).className = "mytcDown")
    }),
    fish.all("#showLinkMore").on("click",
    function() {
        "mytcDown" == fish.dom("span", this).className ? (fish.all("#fdLinklist").css("height:auto;"), fish.dom("span", this).className = "mytcUp") : (fish.all("#fdLinklist").css("height:40px;"), fish.dom("span", this).className = "mytcDown")
    })
});
fish.ready(function() {
    fish.all(".breadcrumb").delegate(".more", "mouseover",
    function(c) {
        fish.one(c.delegateTarget).addClass("open")
    }).delegate(".more", "mouseout",
    function(c) {
        fish.one(c.delegateTarget).removeClass("open")
    }),
    function() {
        function d(a, c, h) {
            a = fish.one(a);
            var i = a.data("__fixed");
            i || (i = {
                top: a[0].currentStyle.top,
                bottom: a[0].currentStyle.bottom
            },
            a.data("__fixed", i), a.data("__fixedUpdate",
            function(g) {
                a.data("__fixed", g),
                d(a, fish.one(window).scrollTop(), fish.one(window).height())
            }));
            var j;
            i.bottom && (j = f.exec(i.bottom)) ? a[0].style.top = "%" === j[2] ? h + c - a.height() - h * parseInt(j[1]) / 100 + "px": h + c - a.height() - parseInt(j[1]) + "px": (j = f.exec(i.top), a[0].style.top = "%" === j[2] ? c + h * parseInt(j[1]) / 100 + "px": c + parseInt(j[1]) + "px")
        }
        function e() {
            var a = fish.all(".fixed");
            if (!fish.browser("ms", 6)) {
                return void a.data("__fixedUpdate",
                function() {})
            }
            if (0 !== a.length) {
                var g = fish.one(window).scrollTop(),
                h = fish.one(window).height();
                a.each(function(c) {
                    d(c, g, h)
                })
            }
        }
        var f = /^(\d+)(.*)$/;
        fish.one(window).on("scroll",
        function() {
            e()
        }).on("resize",
        function() {
            e()
        }),
        e()
    } (),
    function() {
        function e() {
            g.style.visibility = fish.one(window).scrollTop() > 100 ? "visible": "hidden"
        }
        function f() {
            function d() {
                i -= j,
                i > 0 ? h = setTimeout(d, 16) : (i = 0, h = void 0),
                document.documentElement.scrollTop = document.body.scrollTop = i
            }
            if (!h) {
                var i = fish.one(window).scrollTop(),
                j = Math.floor(i / 20);
                h = setTimeout(d, 16)
            }
        }
        var g = fish.dom(".back_top");
        if (g) {
            e(),
            fish.one(window).on("scroll",
            function() {
                e()
            });
            var h;
            fish.one(".back_top").on("click",
            function() {
                f()
            })
        }
    } (),
    function() {
        var c = fish.all(".side_nav .ly_side_arrow"); (fish.browser("ms", 6) || fish.browser("ms", 7)) && c[0] && c.html("<i></i>")
    } (),
    function() {
        var f = fish.dom(".ly_wtm");
        if (f) {
            var g = fish.browser("ms", 6),
            h = fish.one(window).height(),
            i = 0,
            j = fish.one(".ly_wtw_b").offset(window).top - Math.max(document.documentElement.scrollTop, document.body.scrollTop);
            fish.one(window).on("resize",
            function() {
                h = fish.one(window).height()
            }),
            fish.one(".ly_wtw_b").hover(function() {
                if (j = fish.one(".ly_wtw_b").offset(window).top - Math.max(document.documentElement.scrollTop, document.body.scrollTop), i = fish.one(".ly_wtw_b .ly_wtm_con").height(), i > h - j) {
                    if (h > i) {
                        var c = i - (h - j);
                        fish.one(".ly_wtw_b .ly_wtm_con").css("top:" + ( - c - 2) + "px"),
                        fish.one(".ly_wtw_b .ly_side_arrow").css("top:" + (11 + c + 2) + "px")
                    } else {
                        if (g && i > h) {
                            var c = i - (h - j);
                            fish.one(".ly_wtw_b .ly_wtm_con").css("top:" + ( - c - 2) + "px"),
                            fish.one(".ly_wtw_b .ly_side_arrow").css("top:" + (11 + c + 2) + "px")
                        } else {
                            fish.one(".ly_wtw_b .ly_side_arrow").css("top:11px"),
                            fish.one(".ly_wtw_b .ly_wtm_con").css("top:0px")
                        }
                    }
                }
                fish.dom(".ly_wtw_b .ly_wtm_con").style.display = "block"
            },
            function() {
                fish.dom(".ly_wtw_b .ly_wtm_con").style.display = "none"
            })
        }
    } (),
    function() {
        var c = fish.dom(".side_nav .ly_cs");
        lxwm2 = c ? fish.one(".ly_cs_link", c) : null,
        c && lxwm2 && fish.one(c).hover(function() {
            lxwm2.removeClass("ly_cs_link_b").html(lxwm2[0].getAttribute("title")),
            fish.one(".ly_phone_m", c).css("display:block")
        },
        function() {
            lxwm2.addClass("ly_cs_link_b").html(""),
            fish.one(".ly_phone_m", c).css("display:none")
        })
    } ();
    var b = function() {
        function d() {
            fish.ajax({
                url: "http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAxMTk4MV8xMDAxMTdfNDAwMTc0MDAxN18",
                type: "script",
                fn: function() {
                    for (var c = 0,
                    g = f.length; g > c; c++) {
                        f[c]()
                    }
                    e = !0
                }
            })
        }
        var e, f = [];
        return fish.browser("ms", 6) || ("27212007" === getRefid() ? d() : "91" === fish.one("#diycid").val() ? d() : e = !1),
        {
            show: function(c) {
                e === !0 ? c() : void 0 === e && f.push(c)
            }
        }
    } ();
    b.show(function() {
        var c = fish.one("#tcWeiXin"),
        d = fish.one(c.length ? c[0].parentNode: void 0);
        d.hasClass("express_anchor") && (c.html("before", '<div class="QQ_Consult"><a id="QQConsultIcon" href="javascript:;">咨询</a></div>'), BizQQWPA.addCustom({
            nameAccount: "4001740017",
            selector: "QQConsultIcon",
            aty: "0"
        }))
    }),
    b.show(function() {
        var c = fish.one(".side_nav");
        1 === c.length && (c.html("top", '<a id="QQConsultSquare" href="javascript:;" title="QQ在线咨询" ></a>'), BizQQWPA.addCustom({
            nameAccount: "4001740017",
            selector: "QQConsultSquare",
            aty: "0"
        }))
    }),
    b.show(function() {
        var c = fish.all(".logo_right .lhc");
        c.length > 0 && (fish.one(c[0]).html("before", '<li id="BookLoginQQConsultIcon"><a href="javascript:;">咨询</a></li>'), fish.one(c[1]).html("before", '<li id="BookLogoutQQConsultIcon"><a href="javascript:;">咨询</a></li>'), BizQQWPA.addCustom([{
            nameAccount: "4001740017",
            selector: "BookLoginQQConsultIcon",
            aty: "0"
        },
        {
            nameAccount: "4001740017",
            selector: "BookLogoutQQConsultIcon",
            aty: "0"
        }]))
    }),
    function() {
        var e = new Date(2014, 11, 20),
        f = new Date;
        if (! (f.getTime() > e.getTime())) {
            var g = ["http://www.ly.com/", "http://www.ly.com/hotel/", "http://www.ly.com/iflight/", "http://www.ly.com/scenery/", "http://www.ly.com/zizhuyou/", "http://www.ly.com/huochepiao/train/", "http://www.ly.com/guonei/", "http://www.ly.com/dujia/", "http://www.ly.com/youlun/", "http://www.ly.com/dujia/visa/", "http://tuan.ly.com", "http://www.ly.com/vip/", "http://go.ly.com/"];
            if ( - 1 !== g.indexOf(location.protocol + "//" + location.hostname + location.pathname)) {
                var h = fish.browser("ms", 6);
                fish.ready(function() {
                    var d = fish.one("#content").getCss("width"),
                    i = d && 960 == parseFloat(d.replace("px", "")) ? 960 : 1200,
                    j = document.documentElement.clientWidth ? document.documentElement.clientWidth: fish.one(window).width() - 25;
                    fish.one(document.body).html("bottom", '<a class="fixed youlun_actImg" target="_blank" href="http://www.ly.com/youlun/tours-70600.html"></a>');
                    var k = (j - i) / 2 - 150;
                    k = h && 0 > k ? 0 : k,
                    fish.one(".youlun_actImg").css("left:" + k + "px;"),
                    fish.one(window).on("resize",
                    function() {
                        d = fish.one("#content").getCss("width"),
                        i = d && 960 == parseFloat(d.replace("px", "")) ? 960 : 1200,
                        j = document.documentElement.clientWidth ? document.documentElement.clientWidth: fish.one(window).width() - 25,
                        k = (j - i) / 2 - 150,
                        k = h && 0 > k ? 0 : k,
                        fish.one(".youlun_actImg").css("left:" + k + "px;")
                    }),
                    fish.one(".youlun_actImg").hover(function() {
                        if (!h) {
                            var c = parseFloat(fish.one(this).getCss("width").replace("px", ""));
                            this.style.width = c + 68 + "px"
                        }
                    },
                    function() {
                        if (!h) {
                            var c = parseFloat(fish.one(this).getCss("width").replace("px", ""));
                            this.style.width = c - 68 + "px"
                        }
                    })
                })
            }
        }
    } ()
}),
function() {
    fish.ready(function() {
        function c() {
            function h() {
                var b = 0;
                b = l ? i.value.replace(/[^\x00-\xff]/g, "xx").length: i.value.length,
                b > k && (l ? (i.value = i.value.substring(0, m + Math.floor((k - n) / 2)), n = i.value.replace(/[^\x00\xff]/g, "xx").length) : i.value = i.value.substring(0, k)),
                m = i.value.length,
                n = i.value.replace(/[^\x00-\xff]/g, "xx").length
            }
            var i, j, k, l, m, n;
            this.init = function() {
                i = this.txtNote,
                j = this.txtLimit,
                k = this.limitCount,
                l = this.isbyte,
                (null != i || void 0 != i) && (i.onkeydown = function() {
                    h()
                },
                i.onkeyup = function() {
                    h()
                })
            }
        }
        fish.admin.config({
            mPop: {
                v: "0.2.4",
                css: 1,
                g: 2014080501
            }
        });
        var d = new c;
        d.txtNote = fish.dom("#advice_content"),
        d.limitCount = 200,
        d.isbyte = !1,
        d.txtlength = 200,
        d.init(),
        fish.one("#side_link").on("click",
        function() {
            fish.one(this).mPop({
                content: fish.one(".suggest_box")
            })
        }),
        fish.one("#advice_content").on("keyup",
        function() {
            fish.one("#advice_content").css("border: 1px solid #ddd;");
            var e = fish.one("#advice_content").val();
            fish.one("#wordCount").html();
            e.length > 200 && fish.one("#advice_content").css("border: 1px solid #F00;");
            var f = 200 - parseInt(e.length);
            fish.one("#wordCount").html(f)
        }),
        fish.one("#submitSug").on("click",
        function() {
            var e = fish.one("#advice_content").val(),
            f = fish.one(this).attr("page");
            return "" === e || null === e || e.length > 200 ? void fish.one("#advice_content").css("border: 1px solid #F00;") : void fish.ajax({
                url: "http://www.ly.com/AjaxHelper/CMSUserFeedbackHandler.ashx",
                openType: "get",
                type: "jsonp",
                data: "FeedBackContent=" + encodeURIComponent(e) + "&FromPage=" + encodeURIComponent(f),
                fn: function(b) {
                    "1" === b.state ? (fish.one("#advice_content").val(""), fish.one("#wordCount").html("200"), fish.one(this).mPop({
                        content: fish.one(".s_tip_box")
                    })) : alert(b.state)
                },
                err: function() {
                    fish.one("#advice_content").val(""),
                    fish.one("#wordCount").html("200"),
                    fish.one(this).mPop({
                        content: fish.one(".s_tip_box")
                    })
                }
            })
        })
    })
} ();